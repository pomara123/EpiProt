package epiprot.services.ssp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import epiprot.Protein;
import epiprot.services.Service;

public class JPredService extends Service {
	
	private File fastaFile;
	private String sequence;
	
	// usr/local/jpred/
	final private static String JPREDPATH = "/usr/local/jpred/";
	
	private ArrayList<JPredAminoAcid> aminoAcids = new ArrayList<JPredAminoAcid>();

	public JPredService(File fastaFile) throws JPredSequenceTooLongException {
		// TODO Auto-generated constructor stub
		this.fastaFile = fastaFile;
		checkSequenceLength();
	}
	
	private JPredService(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String s;
		String urlString = "";
		try {
            // using the Runtime exec method:
        	String cmd = "perl "+JPREDPATH+"jpredapi submit mode=single format=fasta email=name@domain.com file="+fastaFile.getAbsolutePath()+" name=TestJob";
        	System.out.println(cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
             
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 
            // read the output from the command
            String jobid = "";
            String cmd2 = "";
            while ((s = stdInput.readLine()) != null) {
            	System.out.println(s);
            	if (s.contains("Created JPred job with jobid:")) {
            		jobid = s.substring(30);
            	}
            	else if (s.contains("...or using 'perl")) {
            		String [] array = s.split("'");
            		cmd2 = array[1];
            		cmd2 = cmd2.replace("jpredapi", JPREDPATH+"jpredapi");
            		cmd2 = cmd2.replace("getResults=yes", "getResults=no");
    				cmd2 = cmd2.replace("checkEvery=60", "checkEvery=10");
            	}
            }         
            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println(jobid);
            System.out.println(cmd2);
                        
            //second part of JPred
            Process p2 = Runtime.getRuntime().exec(cmd2);
            
            stdInput = new BufferedReader(new InputStreamReader(p2.getInputStream()));    
            stdError = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
            
            // read the output from the command
            
            while ((s = stdInput.readLine()) != null) {
            	System.out.println(s);
            	if (s.contains("http://www.compbio.dundee.ac.uk")) {
            		urlString = s;
            	}
            }    
            System.out.println(urlString);
            
            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            //System.exit(-1);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//change urlString to .jnet
		urlString = urlString.replace(".results.html", ".jnet");
		
		try {
			URL url = new URL(urlString);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		    String inputLine;
		    String jpredline = "";
		    String jpredConf = "";
		    while ((inputLine = in.readLine()) != null) {
		        System.out.println(inputLine);
		        if (inputLine.contains("jnetpred:")) {
		        	jpredline = inputLine.substring(9);
		        }
		        else if (inputLine.contains("JNETCONF:")) {
		        	jpredConf = inputLine.substring(9);
		        }
		    }
		    String[] jpredlineArray = jpredline.split(",");
		    String[] jpredConfArray = jpredConf.split(",");
		    
		    for(int i = 0; i < jpredlineArray.length; i++) {
		    	JPredAminoAcid aa = new JPredAminoAcid();
		    	aa.setPosition(i+1);
		    	aa.setResidue(sequence.substring(i, i+1));
		    	aa.setPrediction(jpredlineArray[i]);
		    	aa.setConfidenceScore(Integer.parseInt(jpredConfArray[i]));
		    	aminoAcids.add(aa);
		    }
		    
		    in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	private void checkSequenceLength() throws JPredSequenceTooLongException {
		String urlString = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fastaFile));
			Stream<String> lineStream = br.lines();
			Iterator<String> it = lineStream.iterator();
			StringBuilder sb = new StringBuilder();
			it.next();
			while(it.hasNext()) {
				String line = it.next();
				line = line.replace("\n", "");
				sb.append(line);
			}
			br.close();
			
			sequence = sb.toString();
			if (sequence.length() > 800) {
				throw new JPredSequenceTooLongException("Protein sequence is too long. Must be under 800 amino acids.");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public ArrayList<JPredAminoAcid> getAminoAcids() {
		return aminoAcids;
	}

	public void setAminoAcids(ArrayList<JPredAminoAcid> aminoAcids) {
		this.aminoAcids = aminoAcids;
	}
	
	public static void main(String[]args) {
		Protein protein = new Protein("P31749");
		System.out.println("test1");
		JPredService pps;
		try {
			pps = new JPredService(protein.getFastaFile());
			pps.run();
			System.out.println("test2");
			ArrayList<JPredAminoAcid> aaList = pps.getAminoAcids();
			System.out.println(aaList.size());
			for (JPredAminoAcid aa : aaList) {
				System.out.println(aa.toString());
			}
		} catch (JPredSequenceTooLongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
