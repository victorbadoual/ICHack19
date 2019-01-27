import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { Web3 } from 'meteor/ethereum:web3';

import './main.html';

Template.hello.onCreated(function helloOnCreated() {
  // counter starts at 0
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

Template.hello.helpers({
  counter() {
    return Template.instance().counter.get();
  },
});

Template.hello.events({
  'click button'(event, instance) {
    // increment the counter when button is clicked
    instance.counter.set(instance.counter.get() + 1);
  },
});
