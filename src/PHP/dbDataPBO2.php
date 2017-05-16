<?php
 include 'xmlrpc.inc';
 include 'xmlrpcs.inc';

 $servername = "localhost";
 $username = "root";
 $password = "adamafandi";
 $dbname = "pl";
 $conn = "";
 $persons = new xmlrpcval();

 function setUpConnection(){
 global $conn, $servername, $username, $password, $dbname;
 $conn = new mysqli($servername, $username, $password, $dbname);
 if ($conn->connect_error) {
 die("Connection failed: " . $conn->connect_error);
 }
 }

 function getAllPersons(){
 return new xmlrpcresp(populatePerson());
 }

 function addPerson($person){
 global $conn;
 setUpConnection();
 $val = $person->getParam(0);
 $val->structreset();
 $orang = array();
 while(list($key, $v) = $val->structEach()){
 $orang[$key] = $v->scalarVal();
 }
 $sql = "INSERT INTO Person (
 firstName, lastName, sport, years, vegetarian)".
 "VALUES ('"
 . $orang["firstName"]."','"
 . $orang["lastName"]."','"
 . $orang["sport"]."','"
 . $orang["years"]."','"
 . (($orang["vegetarian"]==1)? 1 : 0 )."')";
 if ($conn->query($sql) === TRUE) {
 $msg = "New record created successfully";
 } else {
 $msg = "Error: " . $sql . "<br>" . $conn->error;
 }

 $conn->close();
 return new xmlrpcresp(new xmlrpcval($msg));
 }

 function updatePerson($person){
 global $conn;
 setUpConnection();
 $val = $person->getParam(0);
 $val->structreset();
 $orang = array();
 while(list($key, $v) = $val->structEach()){
 $orang[$key] = $v->scalarVal();
 }
 $sql = "UPDATE Person "
 . "SET "
 . "PersonID = '".$orang["PersonID"]. "', "
 . "firstName = '".$orang["firstName"]."', "
 . "lastName = '".$orang["lastName"]."', "
 . "sport = '".$orang["sport"]."', "
 . "years = '".$orang["years"]."', "
 . "vegetarian = '".(($orang["vegetarian"]==1)? 1 : 0 )."' "
 . "WHERE PersonID = '".$orang["PersonID"]."'";
 if ($conn->query($sql) === TRUE) {
 $msg = "The record has been updated successfully";
 } else {
 $msg = "Error: " . $sql . "<br>" . $conn->error;
 }

 $conn->close();
 return new xmlrpcresp(new xmlrpcval($msg));
 }

 function deletePerson($person){
 global $conn;
 setUpConnection();
 $val = $person->getParam(0);
 $val->structreset();
 $orang = array();
 while(list($key, $v) = $val->structEach()){
 $orang[$key] = $v->scalarVal();
 }
 $sql = "DELETE FROM Person WHERE PersonID = ".$orang["PersonID"];
 if ($conn->query($sql) === TRUE) {
 $msg = "The record has been deleted successfully";
 } else {
 $msg = "Error: " . $sql . "<br>" . $conn->error;
 }

 $conn->close();
 return new xmlrpcresp(new xmlrpcval($msg));
 }

 function populatePerson() {
 global $conn, $persons, $xmlrpcInt,
 $xmlrpcBoolean, $xmlrpcString, $xmlrpcStruct;
 setUpConnection();

 $sql = "SELECT "
 ."PersonID, firstName, lastName, sport, years, vegetarian "
 ."FROM Person";
 $result = $conn->query($sql);
 if ($result->num_rows > 0){
 while ($row = $result->fetch_assoc()) {
 $person = array(new xmlrpcval(array(
 "PersonID" => new xmlrpcval(
 $row["PersonID"],$xmlrpcInt),
 "firstName" => new xmlrpcval(
 $row["firstName"],$xmlrpcString),
 "lastName" => new xmlrpcval(
 $row["lastName"],$xmlrpcString),
 "sport" => new xmlrpcval(
 $row["sport"],$xmlrpcString),
 "years" => new xmlrpcval(
 $row["years"],$xmlrpcInt),
 "vegetarian" => new xmlrpcval(
 $row["vegetarian"],$xmlrpcBoolean)
 ),$xmlrpcStruct));
 $persons->addArray($person);
 }
 } else {
echo "0 results";
 }

 $conn->close();
 return $persons;
 }


 // Declare our signature and provide some documentation.
 $getAllPersons_sig = array(array($xmlrpcArray));
 $getAllPersons_doc = 'Loading persons untuk ditampilkan pada tabel';

 $addPerson_sig = array(array($xmlrpcString,$xmlrpcStruct));
 $addPerson_doc = 'Insert person';

 $updatePerson_sig = array(array($xmlrpcString,$xmlrpcStruct));
 $updatePerson_doc = 'Update person';

 $deletePerson_sig = array(array($xmlrpcString,$xmlrpcStruct));
 $deletePerson_doc = 'Delete person';

 $s = new xmlrpc_server(
 array(
 'personDAO.getAllPersons' =>
 array('function' => 'getAllPersons',
 'signature' => $getAllPersons_sig,
 'docstring' => $getAllPersons_doc),
 'personDAO.deletePerson' =>
 array('function' => 'deletePerson',
 'signature' => $deletePerson_sig,
 'docstring' => $deletePerson_doc),
 'personDAO.updatePerson' =>
 array('function' => 'updatePerson',
 'signature' => $updatePerson_sig,
 'docstring' => $updatePerson_doc),
 'personDAO.addPerson' =>
 array('function' => 'addPerson',
 'signature' => $addPerson_sig,
 'docstring' => $addPerson_doc)),
 true);

 $s->setdebug(3);
 $s->compress_response = true;

 ?>