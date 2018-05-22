package controller;
/**
 * @author Shunhe Wang s3587669
 */

import java.util.ArrayList;
import java.util.Hashtable;
import model.Node;
import model.People;
import model.Relation;

public class DFSrelationSearch {
	public ArrayList<String> search(String fName, String sName, ArrayList<People> people,
			ArrayList<Relation> relations) {
		ArrayList<String> result = new ArrayList<String>();
		Hashtable<String, Node> nodes = new Hashtable<String, Node>(); // Store all people obj£¬primary key is name
		// Establish model
		for (int i = 0; i < people.size(); i++) {
			Node node = new Node(((People) people.get(i)).getUserName());
			nodes.put(node.getName(), node);
		}
		for (int i = 0; i < people.size(); i++) {
			Node node = (Node) nodes.get(((People) people.get(i)).getUserName());
			addChildren(node, relations, nodes);
		}

		Node start = (Node) nodes.get(fName);
		start.setPath(start.getName()); // Set relation path
		Node end = (Node) nodes.get(sName);
		find(start, result, nodes, start, end);

		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}
		return result;
	}

	// Find every node's child nodes
	public void addChildren(Node node, ArrayList<Relation> relations, Hashtable<String, Node> nodes) {
		for (int i = 0; i < relations.size(); i++) {
			Relation r = (Relation) relations.get(i);
			if (!r.getRelationship().equals("Friend")) { // Check every relation is friend relationship or not
				continue;
			}
			String name = "";
			if (r.getFirstName().equals(node.getName())) { // Get another name
				name = r.getSecondName();
			} else if (r.getSecondName().equals(node.getName())) { // Get another name
				name = r.getFirstName();
			}
			if (!name.equals("")) {
				node.getChildren().add((Node) nodes.get(name)); // According to check relation, find out all objs from
																// hashtable
			}
		}
	}

	// Find all path
	public void find(Node node, ArrayList<String> result, Hashtable<String, Node> nodes, Node start, Node end) {
		ArrayList<Node> children = node.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Node child = children.get(i);
			if (!child.equals(start)) { // Avoid to generate a circle through not allowing to pass the route that has
										// past.
				if (node.getPath().indexOf(child.getName()) > 0) {
					continue;
				} else {
					child.setPath(node.getPath() + "-->" + child.getName());
					if (child.equals(end)) { // If find out, add path to arraylist
						result.add(child.getPath());
						continue;
					}
					find(child, result, nodes, start, end); // this method continue to run till all path past.
				}
			}
		}
	}
}
