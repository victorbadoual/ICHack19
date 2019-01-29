import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { Web3 } from 'meteor/ethereum:web3';
import { Meteor } from 'meteor/meteor'
//import { Assets } from 'meteor/assets';

import './main.html';



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

var data;

Template.addPlayerForm.events({
    'submit form': function(){
      event.preventDefault();

      var http = new XMLHttpRequest();
      http.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          checkPic(this.responseText);
        }
      }

      http.open("GET", "http://localhost:8000/testexec.php", true);
      http.send();
  }
});


// BLOCKCHAIN //
function checkPic(data) {
  console.log(data);
  picHash = data;
  getConfirmations(picHash);
}

async function getConfirmations(picHash) {
  try {
    const web3 = new Web3(new Web3.providers.HttpProvider("https://ropsten.infura.io/v3/319b395c598f44f09fca038a955ee367"));
    account = "0x9be463c5932bdcf1e8304b4386e51a1c9a46052a";
    isMatched = getTransaction(account, web3.eth.getBlock('latest').number, picHash, web3.eth);

    if (isMatched) {
     document.getElementbyID("pic_toset").src = "/image.jpeg";
     document.getElementbyID("added_pic").style.display = "";

   }
   else {
     document.getElementbyID("pic_toset").src = "/image.jpeg";
     document.getElementbyID("added_pic").style.display = "";
     document.getElementbyID("isTrue").src = "/fake.jpeg";
   }

  }
  catch (error) {
    console.log(error);
  }
}

function getTransaction(myaccount, endBlockNumber, picHash, eth) {
  console.log("Using endBlockNumber: " + endBlockNumber);
  startBlockNumber = endBlockNumber - 100;
  console.log("Using startBlockNumber: " + startBlockNumber);
  console.log("Searching for transactions to/from account \"" + myaccount + "\" within blocks "  + startBlockNumber + " and " + endBlockNumber);

  for (var i = endBlockNumber; i >= startBlockNumber; i--) {
    if (i % 10 == 0) {
      console.log("Searching block " + i);
    }
    var block = eth.getBlock(i, true);
    if (block != null && block.transactions != null) {
      for (var j = 0; j < block.transactions.length; j++) {
        e = block.transactions[j];
        if (myaccount == e.from && myaccount == e.to && e.input == ASCIItoHEX(picHash)) {
          console.log("Picture certified");
          return true;
        }
      }
    }
  }
  console.log("We haven't found an authentic matching picture.");
  console.log("You're picture is a FAKE!");
  return false;
}

function ASCIItoHEX(ascii) {
    hex = "";
    for (i = 0; i < ascii.length; i++) {
        ch = ascii.charCodeAt(i);
        part = ch.toString(16);
        hex += part;
    }
    return "0x" + hex;
}








    //var data = httpGet("http://localhost:8000/testexec.php");

    //console.log(data);



    console.log(event.type);
