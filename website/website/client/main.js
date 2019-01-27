import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { Web3 } from 'meteor/ethereum:web3';
import { Meteor } from 'meteor/meteor'
//import { Assets } from 'meteor/assets';

import './main.html';

Template.hello.onCreated(function helloOnCreated() {
  // counter starts at 0

//var data = Assets.getText("example.txt").toString().split("\n");
//console.log(data); // File contents as utf8 encoded string.

  //var fileContents = Assets.getText('hash.txt');
  //const data = Assets.getText('public/data.txt');
  //console.log("fileContents");




  this.counter = new ReactiveVar(0);
  console.log("hello");
  const web3 = new Web3(new Web3.providers.HttpProvider("https://ropsten.infura.io/v3/319b395c598f44f09fca038a955ee367"));
  options = {from: "0x9be463c5932bdcf1e8304b4386e51a1c9a46052a"};
  var filter = web3.eth.filter(options);
  var results = filter.get(function (error, log) {
	 console.log(log); //  {"address":"0x0000000000000000000000000000000000000000", "data":"0x0000000000000000000000000000000000000000000000000000000000000000", ...}
	});
  console.log(results);
});



// function httpGet(theUrl)
// {
//     var xmlHttp = new XMLHttpRequest();
//     xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
//     xmlHttp.send( null );
//     return xmlHttp.responseText;
// }

Template.hello.events({
  'click button'(event, instance) {
    // increment the counter when button is clicked
    const client = filestack.init('Anq9aCPm7QzOirvCG9gmmz');
    const options = {
   uploadInBackground: false,
   onUploadDone: showFileData
 };

 client.picker(options).open();
  },
});


Template.addPlayerForm.events({
    'submit form': function(){
      event.preventDefault();




      const Http = new XMLHttpRequest();
  const url='http://localhost:8000/testexec.php';
  Http.open("GET", url);
  Http.send();
  Http.onreadystatechange=(e)=>{
  console.log(Http.responseText)
  //data = Http.responseText
  }


    //var data = httpGet("http://localhost:8000/testexec.php");

    //console.log(data);



    console.log(event.type);


    }
});
