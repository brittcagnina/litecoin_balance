package com.coinhark.litecoinbalance.utils;

public class AddressChecker {
/*
 * Inspired by Gavin Andresen's post: https://bitcointalk.org/index.php?topic=1026.0
 */
        
        //this is a hex byte; 00 for BTC;
        private final String ADDRESS_VERSION = "30";
        
        public static void main(String[] args) {
                AddressChecker ac = new AddressChecker();
                ac.run("LYn9mNKQbWagdVqsGNDcVcU3ggncZ6fAgh");
                ac.run("LYr9JdPBJ2bgQGNUg4Ez2WG8ayAQ3mPyR1");
                ac.run("LRWUJhm2bTNfrQtAhpugCAnpbt657WwnZm");
                ac.run("LV4svi9YUpZBiFktVNLAewintyipVQcsUS");
                ac.run("LeUaTNUka3WqwwVoKu1M6WbwUAMCVUdhjt");
                ac.run("LP4j8Q4gpyJVLPfPJENHQmxHEdtTGK43Y3");
                ac.run("LfuqSkUiBC2zxLBhtUCP86evNW3LjXQG9q");

                ac.run("LfuqSkUiBC2zZLBhtUCP86evNW3LjXQG9q");
                ac.run("LV4svi9YUpZBiFktVNLAewintyipVQcsUs");
                ac.run("LYr9JdPBJ2bgQGNUg4Ez2WG8ayAQ3mPyR1R1");
                
                ac.run("1Sq2Pb5pCfPBKZ3U4SFjHMlhvAbz8Vpjk");
                ac.run("1C8UDzgYqlhFqduhYzuAjlMQZWBvYahvD1");
                ac.run("17FSKMPAyXGR7EQziCqbVfwleGumRosQoh");
        }
        
        public boolean run(String address) {
                
            byte[] addressBase58 = Base58.decode(address);
	        if(addressBase58.length != 25) {
	                return false;
	        }
	        
	        String version = Utils.bytesToHex(addressBase58[0]);
	        
	        int versionInt= Integer.parseInt(version, 16);
	        int versionAddressInt= Integer.parseInt(ADDRESS_VERSION, 16);
	
	        if(versionInt > versionAddressInt) {
	                return false;
	        }
	        //Log.d("[Litecoin Balance]", "Litecoin Version: " + versionAddressInt);
	        //Log.d("[Litecoin Balance]", "Address Version : " + versionInt);
	        
	        byte[] checksum = new byte[4];
	        for(int i = 0; i < 4; i++) {
	                checksum[i] = addressBase58[addressBase58.length - 4 + i];
	        }
	        
	        byte[] vh160 = new byte[addressBase58.length - 4];
	        for(int i = 0; i < addressBase58.length - 4; i++) {
	                vh160[i] = addressBase58[i];
	        }
	
	        byte[] subH3 = new byte[4];
	        byte[] h3 = Crypto.dsha256(vh160);
	        for(int i = 0; i < 4; i++) {
	                subH3[i] = h3[i];
	        }
	        
	        String subH3Hex = Utils.bytesToHex(subH3);
	        String checkSumhex = Utils.bytesToHex(checksum);
	        
	        //Log.d("[Litecoin Balance]", wtfHex);
	        //Log.d("[Litecoin Balance]", checkSumhex);
	        //Log.d("[Litecoin Balance]", "---------");
	        if(checkSumhex.equals(subH3Hex)) {
	                return true;
	        }
	        return false;
        }
}