package com.example.cameraapp;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

public class BlockPusher {

    public static void main(String[] args) throws IOException, CipherException {
        //String hash = "THEO COHEN IS IN THE MASTER OF THE BLOCKCHAIN";
        //BlockPusher.pushToBlock(hash);
        //System.out.println(retrieveHash("0x44494e472044494e4720444f4e47"));
    }

    public static void pushToBlock(String hash) throws IOException, CipherException {

        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/319b395c598f44f09fca038a955ee367"));

        Credentials credentials = WalletUtils.loadCredentials("helloworld", "app/src/main/wallet_file/wallet_file");

        // get the next available nonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        BigInteger gas_price = DefaultGasProvider.GAS_PRICE; //Convert.toWei("0.000021", Convert.Unit.GWEI).toBigInteger();
        BigInteger gas_limit = DefaultGasProvider.GAS_LIMIT; //Convert.toWei("0.000021", Convert.Unit.GWEI).toBigInteger();;
        String address = credentials.getAddress();
        BigInteger value = new BigInteger("0");

        // create our transaction
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce, gas_price, gas_limit, address, value, ASCIItoHEX(hash));

        // sign & send our transaction
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        System.out.println(ethSendTransaction.getTransactionHash());


        web3j.shutdown();
    }

    public static String ASCIItoHEX(String ascii) {
        // Initialize final String
        String hex = "";

        // Make a loop to iterate through
        // every character of ascii string
        for (int i = 0; i < ascii.length(); i++) {

            // take a char from
            // position i of string
            char ch = ascii.charAt(i);

            // cast char to integer and
            // find its ascii value
            int in = (int) ch;

            // change this ascii value
            // integer to hexadecimal value
            String part = Integer.toHexString(in);

            // add this hexadecimal value
            // to final string.
            hex += part;
        }
        // return the final string hex
        return hex;
    }
    /*
    public static void lookUp(Web3j web3j) {

        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST, <contract-address>)
             [.addSingleTopic(...) | .addOptionalTopics(..., ...) | ...];

        web3j.ethLogFlowable(filter).subscribe(log -> {

        });



        Subscription subscriptionToBlocks = web3j.blockObservable(true).subscribe(block -> {
            List<EthBlock.TransactionResult> transactions = block.getBlock().getTransactions();
            if(transactions.size > 0){
                transactions.forEach(tx -> {
                    EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
                    System.out.println("transaction data:"+transaction.getInput());
                }
            }
        }
    }

    public static String retrieveHash(String hexString) {
        //String hexString = transaction.getInput();

        // Remove the prefix 0x
        if (hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }

        // Convert Base16 into a byte array
        byte[] byteArray = new BigInteger(hexString, 16).toByteArray();

        // Pass the byte array to a String will re-encode it in ASCII.
        String asciiString = new String(byteArray);
        //System.out.println(asciiString);
        return asciiString;
    }

    public static boolean lookUpHash(String hash) {
        return false;
    }
    */
}
