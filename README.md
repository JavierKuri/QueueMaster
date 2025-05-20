# Title: QueueMaster

# Author: Javier Kuri
QueueMaster is a queuing theory Java application that lets the user create finite graphs, visualize them, calculate their probabilities and relevant values, and export them into JSON files.

## Installation

There are two ways to get QueueMaster running:

1.- Run the Main.java file directly from the project.

2.- Compile the project into a jar file by running the following commands in the root of the project:

```bash
mvn clean package
java -jar target/QueueMaster.jar
```

## Usage

### Save button
Allows the user to save the current graph into a JSON file. there is a text field for changing the file name. The files will be saved under QueueMaster/graph_files/.

### Load button
Allows the user to load a graph from a json file. The specific JSON structure is required. The following is an example of a JSON file created with the save button (though only the nodes JSON array is necessary for loading): 

```json
{
  "lambda_test": 0.5,
  "s": 1,
  "nodes": [
    {
      "lambda_out": 1,
      "probability": 0.5,
      "mu_in": 1,
      "n": 0
    },
    {
      "lambda_out": 0,
      "probability": 0.5,
      "mu_in": 0,
      "n": 1
    }
  ],
  "lq": 0,
  "mu_test": 0.5,
  "w": 1,
  "wq": 0,
  "l": 0.5
}
```
### Add node button
Every graph will start with the 0 node and will be considered empty. To add a node, the user must insert the lambda_in and mu_out values for the next node in the queue, and then press the add node button. the node will be added to the queue on the screen.

### Set s button
The set s button sets the number of service channels in the queue. Once set it cannot be changed until the queue is cleared.

### Calculate probabilities button
The calculate probabiliites button will calculate the probabilities for each node in the queue and display them on the screen.

### Calculate queue values button
The calculate queue values button will calculate the rho, L, Lq, W, and Wq for the queue and display them on screen.

### Clear button
The clear button will clear the graph and text inserted on the screen, allowing the user to create another graph form zero.