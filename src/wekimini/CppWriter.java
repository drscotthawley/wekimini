/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import wekimini.learning.KNNModelBuilder;
import wekimini.learning.Model;
import wekimini.learning.NeuralNetModelBuilder;
import wekimini.learning.NeuralNetModelBuilder.HiddenLayerType;
import wekimini.learning.NeuralNetworkModel;
import wekimini.osc.OSCOutput;

/**
 *
 * @author mzed
 */
public class CppWriter {

    private static final Logger logger = Logger.getLogger(CppWriter.class.getName());

    public void writeToFiles(String filename, int numExamples, int numInputs, OSCOutput output, LearningModelBuilder modelBuilder, Instances insts, Model model) throws IOException {
        if (modelBuilder instanceof NeuralNetModelBuilder) {
            try {
                writeNNModel(filename, numExamples, numInputs, modelBuilder, model);
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Could not write to NN model to Cpp file {0}", ex.getMessage());
            }
        } else if (modelBuilder instanceof KNNModelBuilder) {
            try {
                writeKNNModel(filename, numExamples, numInputs, output, modelBuilder, insts);
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Could not write kNN model to Cpp file {0}", ex.getMessage());
            }
        } else {
            logger.log(Level.INFO, "Cannot write C++ for this kind of model.");
        }
    }

    private void writeKNNModel(String filename, int numExamples, int numInputs, OSCOutput output, LearningModelBuilder modelBuilder, Instances insts) throws IOException {
        //Get numNeighbours from modelBuilder and numClasses from output
        int numNeighbours = 1;
        int numClasses = 1;
        try {
            KNNModelBuilder kmb = (KNNModelBuilder) modelBuilder;
            numNeighbours = kmb.getNumNeighbors();
            //FIXME: Use above syntax for this method. -MZ
            Method getNumClasses = output.getClass().getMethod("getNumClasses", (Class<?>[]) null);
            numClasses = (int) getNumClasses.invoke(output, (Object[]) null);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Could not write to Cpp file {0}", ex.getMessage());
        }
        
        //Write header
        String headerName = filename + ".h";
        FileWriter headerWrite = new FileWriter(headerName, true);
        try (PrintWriter headerPrint = new PrintWriter(headerWrite)) {
            headerPrint.printf("#ifndef classify_h\n");
            headerPrint.printf("#define classify_h\n\n");
            headerPrint.printf("#define NUM_NEIGHBOURS " + numNeighbours + "\n");
            headerPrint.printf("#define NUM_CLASSES " + numClasses + "\n");
            headerPrint.printf("#define NUM_EXAMPLES " + numExamples + "\n");
            headerPrint.printf("#define NUM_FEATURES " + numInputs + "\n\n");
            headerPrint.printf("struct neighbour {\n");
            headerPrint.printf("	int classNum;\n");
            headerPrint.printf("	double features[NUM_FEATURES];\n");
            headerPrint.printf("};\n\n");
            headerPrint.printf("class knnClassification {\n\n");
            headerPrint.printf("public:\n");
            headerPrint.printf("	knnClassification();\n");
            headerPrint.printf("	int getClass(double* inputVector);\n\n");
            headerPrint.printf("private:\n");
            headerPrint.printf("	neighbour neighbours[NUM_EXAMPLES];\n");
            headerPrint.printf("	int foundClass;\n");
            headerPrint.printf("};\n\n");
            headerPrint.printf("#endif");
        }

        //Write cpp
        String cppName = filename + ".cpp";
        FileWriter cppWrite = new FileWriter(cppName, true);
        try (PrintWriter cppPrint = new PrintWriter(cppWrite)) {
            cppPrint.printf("#include <math.h>\n");
            cppPrint.printf("#include <utility>\n");
            cppPrint.printf("#include \"classify.h\"\n\n");
            cppPrint.printf("knnClassification::knnClassification() {\n");
            for (int i = 0; i < numExamples; i++) {
                String[] splitInstance = insts.instance(i).toString().split(",");
                cppPrint.printf("	neighbours[" + i + "] = {");
                cppPrint.printf(splitInstance[splitInstance.length - 1] + ", {");
                for (int j = 3; j < numInputs + 3; j++) {
                    if (j > 3) {
                        cppPrint.printf(", ");
                    }
                    cppPrint.printf(splitInstance[j]);
                }
                cppPrint.printf("}};\n");
            }
            cppPrint.printf("	foundClass = 0;\n}\n\n");
            cppPrint.printf("int knnClassification::getClass(double* inputVector) {\n");
            cppPrint.printf("	std::pair<int, double> nearestNeighbours[NUM_NEIGHBOURS];\n");
            cppPrint.printf("	std::pair<int, double> farthestNN = {0, 0.};\n");
            cppPrint.printf("	//Find k nearest neighbours\n");
            cppPrint.printf("	for (int i = 0; i < NUM_EXAMPLES; i++) {\n");
            cppPrint.printf("		//find Euclidian distance for this neighbor\n");
            cppPrint.printf("		double euclidianDistance = 0;\n");
            cppPrint.printf("		for(int j = 0; j < NUM_EXAMPLES ; j++){\n");
            cppPrint.printf("			euclidianDistance = euclidianDistance + pow((inputVector[j] - neighbours[i].features[j]),2);\n");
            cppPrint.printf("		}\n");
            cppPrint.printf("		euclidianDistance = sqrt(euclidianDistance);\n");
            cppPrint.printf("		if (i < NUM_NEIGHBOURS) {\n");
            cppPrint.printf("			//save the first k neighbours\n");
            cppPrint.printf("			nearestNeighbours[i] = {i, euclidianDistance};\n");
            cppPrint.printf("			if (euclidianDistance > farthestNN.second) {\n");
            cppPrint.printf("				farthestNN = {i, euclidianDistance};\n}\n");
            cppPrint.printf("		} else if (euclidianDistance < farthestNN.second) {\n");
            cppPrint.printf("			//replace farthest, if new neighbour is closer\n");
            cppPrint.printf("			nearestNeighbours[farthestNN.first] = {i, euclidianDistance};\n");
            cppPrint.printf("			farthestNN = {i, euclidianDistance};\n}\n}\n");
            cppPrint.printf("	//majority vote on nearest neighbours\n");
            cppPrint.printf("	int numVotesPerClass[NUM_CLASSES] = {};\n");
            cppPrint.printf("	for (int i = 0; i < NUM_NEIGHBOURS; i++){\n");
            cppPrint.printf("		numVotesPerClass[neighbours[nearestNeighbours[i].first].classNum - 1]++;\n");
            cppPrint.printf("	}\n");
            cppPrint.printf("	foundClass = 0;\n");
            cppPrint.printf("	int mostVotes = 0;\n");
            cppPrint.printf("	for (int i = 0; i < NUM_CLASSES; i++) {\n");
            cppPrint.printf("		if (numVotesPerClass[i] > mostVotes) { //TODO: Handle ties the same way Wekinator does\n");
            cppPrint.printf("			mostVotes = numVotesPerClass[i];\n");
            cppPrint.printf("			foundClass = i + 1;\n");
            cppPrint.printf("		}\n");
            cppPrint.printf("	}\n");
            cppPrint.printf("	return foundClass;\n");
            cppPrint.printf("}\n");
        }
    }

    private void writeNNModel(String filename, int numExamples, int numInputs, LearningModelBuilder modelBuilder, Model model) throws IOException {
        int numHiddenNodes = 1;
        int numHiddenLayers = 1;
        String modelDescription = "";

        try {
            NeuralNetModelBuilder nmb = (NeuralNetModelBuilder) modelBuilder;
            HiddenLayerType htp = nmb.getHiddenLayerType();
            if (htp == HiddenLayerType.NUM_FEATURES) {
                numHiddenNodes = numInputs;
            } else {
                numHiddenNodes = nmb.getNumNodesPerHiddenLayer();
            }
            numHiddenLayers = nmb.getNumHiddenLayers();
            
            NeuralNetworkModel nnm = (NeuralNetworkModel) model;
            modelDescription = nnm.getModelDescription();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Could not write to Cpp file {0}", ex.getMessage());
        }
           
        if (numHiddenLayers > 1) {
            logger.log(Level.WARNING, "Cannot write Cpp model with {0} layers.", numHiddenLayers);
        }
        
        //Write header
        String headerName = filename + ".h";
        FileWriter headerWrite = new FileWriter(headerName, true);
        try (PrintWriter headerPrint = new PrintWriter(headerWrite)) {
            headerPrint.printf("#ifndef neuralNetwork_h\n");
            headerPrint.printf("#define neuralNetwork_h\n\n");
            headerPrint.printf("#define NUM_INPUTS " + numInputs + "\n");
            headerPrint.printf("#define NUM_HIDDEN " + numHiddenNodes + "\n");
            headerPrint.printf("#define NUM_OUTPUT 1\n\n");
            headerPrint.printf("class neuralNetwork {\n\n");
            headerPrint.printf("public:\n\n");
            headerPrint.printf("	neuralNetwork();\n");
            headerPrint.printf("	~neuralNetwork();\n\n");
            headerPrint.printf("	double feedForward(double* pattern);\n\n");
            headerPrint.printf("private:\n\n");
            headerPrint.printf("	double* inputNeurons;\n");
            headerPrint.printf("	double* hiddenNeurons;\n");
            headerPrint.printf("	double** wInputHidden;\n");
            headerPrint.printf("	double* wHiddenOutput;\n\n");
            headerPrint.printf("	double output;\n\n");
            headerPrint.printf("	inline double activationFunction(double x);\n");
            headerPrint.printf("};\n\n");
            headerPrint.printf("#endif\n");
        }

        //Write cpp
        String cppName = filename + ".cpp";
        FileWriter cppWrite = new FileWriter(cppName, true);
        try (PrintWriter cppPrint = new PrintWriter(cppWrite)) {
            cppPrint.printf("#include <math.h>\n");
            cppPrint.printf("#include \"model-cpp.0.h\"\n\n"); //FIXME: Make this correct for every model. MZ
            cppPrint.printf("neuralNetwork::neuralNetwork() {\n\n");
            cppPrint.printf("	//input neurons, including bias\n");
            cppPrint.printf("	inputNeurons = new(double[NUM_INPUTS + 1]);\n");
            cppPrint.printf("	for (int i=0; i < NUM_INPUTS; i++){\n");
            cppPrint.printf("		inputNeurons[i] = 0;\n");
            cppPrint.printf("	}\n");
            cppPrint.printf("	inputNeurons[NUM_INPUTS] = -1;\n\n");
            cppPrint.printf("	//hidden neurons, including bias\n");
            cppPrint.printf("	hiddenNeurons = new(double[NUM_HIDDEN + 1]);\n");
            cppPrint.printf("	for (int i=0; i < NUM_HIDDEN; i++){\n");
            cppPrint.printf("		hiddenNeurons[i] = 0;\n");
            cppPrint.printf("	}\n");
            cppPrint.printf("	hiddenNeurons[NUM_HIDDEN] = -1;\n");
            cppPrint.printf("	\n");
            cppPrint.printf("	//output\n");
            cppPrint.printf("	output = 0;\n\n");
            cppPrint.printf("	//weights between input and hidden\n");
            cppPrint.printf("	wInputHidden = new(double*[NUM_INPUTS + 1]);\n");
            cppPrint.printf("	for (int i = 0; i <= NUM_INPUTS; i++) {\n");
            cppPrint.printf("		wInputHidden[i] = new (double[NUM_HIDDEN]);\n");
            cppPrint.printf("		for (int j=0; j < NUM_HIDDEN; j++) {\n");
            cppPrint.printf("			wInputHidden[i][j] = 0;\n");
            cppPrint.printf("		}\n");
            cppPrint.printf("	}\n");
            
            String modelDescriptionLines[] = modelDescription.split("\\r?\\n");
            for (int i = 0; i < numInputs; i++) {
                for (int j = 0; j < numHiddenNodes; j++) {
                    int offset = (2 + numHiddenNodes) + (j * (3 + numInputs)) + 4 + i; //TODO: Make this look better. MZ
                    String nodeWeight[] = modelDescriptionLines[offset].split("\\s+");
                    cppPrint.printf("	wInputHidden[" + i + "][" + j + "] = " + nodeWeight[3] + ";\n");
                }
            }
            for (int j = 0; j < numHiddenNodes; j++) {
                int offset = (2 + numHiddenNodes) + (j * (3 + numInputs)) + 3; //TODO: Make this look better. MZ
                String biasWeight[] = modelDescriptionLines[offset].split("\\s+");
                cppPrint.printf("	wInputHidden[" + numInputs + "][" + j + "] = " + biasWeight[2] + ";\n");
            }
            
            cppPrint.printf("\n");
            cppPrint.printf("	//weights between hidden and output\n");
            cppPrint.printf("	wHiddenOutput = new(double[NUM_HIDDEN + 1]);\n");
            cppPrint.printf("	for (int i = 0; i <= NUM_HIDDEN; i++) {\n");
            cppPrint.printf("		 wHiddenOutput[i] = 0;\n");
            cppPrint.printf("	}\n");          
            for (int i = 0; i < numHiddenNodes; i++) {
                String nodeWeight[] = modelDescriptionLines[i + 3].split("\\s+");
                cppPrint.printf("	wHiddenOutput[" + i + "] = " + nodeWeight[3] + ";\n");
            }
            String biasWeight[] = modelDescriptionLines[2].split("\\s+");
            cppPrint.printf("	wHiddenOutput[" + numHiddenNodes + "] = " + biasWeight[2] + ";\n");            
            cppPrint.printf("}\n\n");
            
            cppPrint.printf("neuralNetwork::~neuralNetwork() {\n");
            cppPrint.printf("	delete[] inputNeurons;\n");
            cppPrint.printf("	delete[] hiddenNeurons;\n\n");
            cppPrint.printf("	for (int i=0; i <= NUM_INPUTS; i++) {\n");
            cppPrint.printf("		delete[] wInputHidden[i];\n");
            cppPrint.printf("	}\n");
            cppPrint.printf("	delete[] wInputHidden;\n\n");
            cppPrint.printf("	delete[] wHiddenOutput;\n");
            cppPrint.printf("}\n\n");
            
            cppPrint.printf("inline double neuralNetwork::activationFunction(double x) {\n");
            cppPrint.printf("	//sigmoid\n");
            cppPrint.printf("	return 1/(1 + exp(-x));\n");
            cppPrint.printf("}\n\n");

            cppPrint.printf("double neuralNetwork::feedForward(double* pattern) {\n");
            cppPrint.printf("	//set input layer\n");
            cppPrint.printf("	for (int i = 0; i < NUM_INPUTS; i++) {\n");
            cppPrint.printf("		inputNeurons[i] = pattern[i];\n");
            cppPrint.printf("	}\n\n");
            cppPrint.printf("	//calculate hidden layer and output\n");
            cppPrint.printf("	output = 0;\n");
            cppPrint.printf("	for (int j=0; j < NUM_HIDDEN; j++) {\n");
            cppPrint.printf("		hiddenNeurons[j] = 0;\n");
            cppPrint.printf("		for (int i = 0; i <= NUM_INPUTS; i++) {\n");
            cppPrint.printf("			hiddenNeurons[j] += inputNeurons[i] * wInputHidden[i][j];\n");
            cppPrint.printf("		}\n");
            cppPrint.printf("		hiddenNeurons[j] = activationFunction(hiddenNeurons[j]);\n");
            cppPrint.printf("           output += hiddenNeurons[j] * wHiddenOutput[j];\n");
            cppPrint.printf("	}\n");
            cppPrint.printf("	output += hiddenNeurons[NUM_HIDDEN] * wHiddenOutput[NUM_HIDDEN];\n");
            cppPrint.printf("	return output;\n");
            cppPrint.printf("}\n\n");
            cppPrint.printf("/* Full model description\n");
            cppPrint.printf(modelDescription + "\n");
            cppPrint.printf("*/\n");
        }
    }
}
