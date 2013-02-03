package net.peerindex.challenge.webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class URLStream implements Iterator<URL> {
	private int _currentfile = 0;
	private File[] _listOfFiles;
	private FileInputStream fis;
	private String _tempstring;
	private BufferedReader bufferedReader;
	
    private final String _path = "./resources";
    private final String _filename_part = "part-m-";
    private final int _numberfiletocrawl = 1;
    
    public URLStream() {
		_listOfFiles = new File[_numberfiletocrawl];
		
		for(int i = 0 ; i<_numberfiletocrawl ; i++)
			_listOfFiles[i] = new File(_path + "/" + _filename_part + String.format("%05d",i));
		
		
		try {
			fis = new FileInputStream(_listOfFiles[_currentfile]);
			bufferedReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(_listOfFiles.length);
	}

	@Override
	public boolean hasNext() {
		try {
			String line = bufferedReader.readLine();
			if (line != null) {
				_tempstring = line;
				return true;
			} else {
				while (_currentfile + 1 < _listOfFiles.length) {
					bufferedReader.close();
					fis.close();
					_currentfile++;
					System.out.println(_listOfFiles[_currentfile].getName());
					try {
						fis = new FileInputStream(_listOfFiles[_currentfile]);
						bufferedReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					line = bufferedReader.readLine();
					if (line != null) {
						_tempstring = line;
						return true;
					}
				}
				return false;
			}

		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public URL next() {
		try {
			String[] splitArray = _tempstring.split("\"");
	   		//System.out.println(splitArray[6]);     	
			return new URL(splitArray[3]);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
