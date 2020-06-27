package org.upgrad.capstone.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.upgrad.capstone.config.ConfigParameters;

import java.io.IOException;
import java.io.Serializable;

public class HBaseConnection implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Admin hbaseAdmin = null;


    public static Connection getHbaseConnection() throws IOException {
        Connection connection = null;
        try {
            if (hbaseAdmin == null) {
                org.apache.hadoop.conf.Configuration conf = (org.apache.hadoop.conf.Configuration) HBaseConfiguration.create();
                conf.setInt("timeout", 1200);
                conf.set("hbase.master", ConfigParameters.EC2_PUBLIC_DNS + ":60000");
                conf.set("hbase.zookeeper.quorum", ConfigParameters.EC2_PUBLIC_DNS);
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
