# BSTPortal (Binary Search Tree Portal)
A BST portal with front end and backend components to manage BST. This was part of a coding assignment.

## Question
Make a service where user can create/maintain/manage Binary Search Trees(BST).

Here are the use cases:
BST should be of integers.
BST should be persisted for a user.
No auth required for user as it will be a single session application. So if the user closes browser window he will loose session.

1. User should be able to create a BST using restful endpoints.
2. User should be able to add/remove nodes using restful endpoints
3. User should be able to get position of a node provided in a text-form-input. In case the node isn't present return custom exception.
4. API to return stats of BST:
	- Size 		: no. of nodes
	- Max Depth
	- Min depth
5. API to print the tree. Print should be done using HTML.

Solution:
1. BSTService : Backend (Rest services built using Vertx in Java)
2. BSTFrontEnd : FrontEnd (Built using AngularJs, BootstrapCSS, Express and NodeJs )

Assumptions: Binary Search Tree doesn't contain any duplicates. If duplicate node is inserted, it will throw error.

Persistent Storage: There is a in memory data store which stores session and its associated BST. Currently the data is not stored in any DB or FS.

Prerequisites:
Please install npm and bower for front-end server in node.js

Steps:
(Backend)
1. Go to BSTService folder.
2. Do "mvn clean install"
3. Run class "BSTServiceHttpVertbox.java" to start backend.

(FrontEnd)
1. Go to BSTPortal folder.
2. Run 'setup.bat' on Windows (or) 'setup.sh' in linux.
3. Start now by visting "http://localhost:8080/home"
