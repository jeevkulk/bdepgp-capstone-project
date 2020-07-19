package org.upgrad.capstone.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.upgrad.capstone.config.Options;

import java.io.IOException;
import java.io.Serializable;

/**
 * Get HBase connection
 */
public class HBaseConnection implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Admin hbaseAdmin = null;

    /**
     * Obtains Hbase connection
     * @return
     * @throws IOException
     */
    public static Connection getHbaseConnection() {
        Options options = Options.getInstance();
        Connection connection = null;
        try {
            if (hbaseAdmin == null) {
                org.apache.hadoop.conf.Configuration conf = (org.apache.hadoop.conf.Configuration) HBaseConfiguration.create();
                conf.setInt("timeout", 1200);
                conf.set("hbase.master", options.getEC2_PUBLIC_DNS() + ":60000");
                conf.set("hbase.zookeeper.quorum", options.getEC2_PUBLIC_DNS());
                conf.set("hbase.zookeeper.property.clientPort", "2181");
                conf.set("zookeeper.znode.parent", "/hbase");
                connection = ConnectionFactory.createConnection(conf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
