package net.peerindex.challenge.webcrawler;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseKeyValueStore implements KeyValueStore {

	private HTable table;

	public HBaseKeyValueStore() throws IOException {
		Configuration c = HBaseConfiguration.create();

		table = new HTable(c, "test");
	}

	@Override
	public boolean contains(String key) {
		try {
			return table.exists(new Get(key.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String get(String key) {

		String ret = null;
		try {
			ret = table.get(new Get(key.getBytes())).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public boolean put(String key, String value) {

		try {
			Put put = new Put(key.getBytes());
			put.add(Bytes.toBytes("details"), Bytes.toBytes("page"),
					value.getBytes());
			table.put(put);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(String key) {
		// TODO Auto-generated method stub

		try {
			table.delete(new Delete(key.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
