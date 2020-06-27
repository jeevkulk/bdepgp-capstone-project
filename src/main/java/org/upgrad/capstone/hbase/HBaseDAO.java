package org.upgrad.capstone.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.upgrad.capstone.domain.CardLookup;
import org.upgrad.capstone.domain.CardTransaction;

import java.io.IOException;
import java.util.Objects;

/**
 * HBase DAO class that helps fetch required data from HBase
 */
public class HBaseDAO {

    public CardLookup lookupCardDetails(CardTransaction cardTransaction) throws IOException {
        Connection connection = HBaseConnection.getHbaseConnection();
        Table table = null;
        CardLookup cardLookup = new CardLookup();
        try {
            Get cardId = new Get(Bytes.toBytes(cardTransaction.getCardId()));
            table = connection.getTable(TableName.valueOf("card_lookup"));
            Result result = table.get(cardId);
            //System.out.println(result);
            //System.out.println("Row key : " + new String(result.getRow()));
            //System.out.println(result.getColumnLatestCell(Bytes.toBytes("postcode"), Bytes.toBytes("postcode")));
            String postCode = Bytes.toString(result.getValue(Bytes.toBytes("postcode"), Bytes.toBytes("postcode")));
            String ucl = Bytes.toString(result.getValue(Bytes.toBytes("ucl"), Bytes.toBytes("ucl")));
            String transactionDate = Bytes.toString(result.getValue(Bytes.toBytes("postcode"), Bytes.toBytes("transaction_dt")));
            String score = Bytes.toString(result.getValue(Bytes.toBytes("score"), Bytes.toBytes("score")));
            cardLookup.setCardId(cardTransaction.getCardId());
            cardLookup.setUcl(Objects.nonNull(ucl) ? Double.parseDouble(ucl) : 0.0d);
            cardLookup.setPostCode(Objects.nonNull(postCode) ? postCode : null);
            cardLookup.setTransactionDate(Objects.nonNull(transactionDate) ? Long.parseLong(transactionDate) : 0L);
            cardLookup.setScore(Objects.nonNull(score) ? Integer.parseInt(score) : 0);
        } catch (IOException e) {
            throw e;
        } finally {
            table.close();
        }
        return cardLookup;
    }

    public void saveCardLookupDetails(CardTransaction cardTransaction) throws IOException {
        Connection connection = HBaseConnection.getHbaseConnection();
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("card_lookup_test"));
            Put put = new Put(Bytes.toBytes(cardTransaction.getCardId()));
            put.addColumn(
                Bytes.toBytes("postcode"),
                Bytes.toBytes("postcode"),
                Bytes.toBytes(cardTransaction.getPostCode())
            );
            put.addColumn(
                Bytes.toBytes("postcode"),
                Bytes.toBytes("transaction_dt"),
                Bytes.toBytes(cardTransaction.getTransactionDate())
            );
            table.put(put);
        } catch (IOException e) {
            throw e;
        } finally {
            table.close();
        }
    }

    public void saveCardTransaction(CardTransaction cardTransaction) throws IOException {
        Connection connection = HBaseConnection.getHbaseConnection();
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("card_transaction_test"));
            Put put = new Put(Bytes.toBytes(cardTransaction.getCardId()));
            put.addColumn(
                Bytes.toBytes("card_id"),
                Bytes.toBytes("card_id"),
                Bytes.toBytes(cardTransaction.getCardId())
            );
            put.addColumn(
                Bytes.toBytes("transaction_details"),
                Bytes.toBytes("amount"),
                Bytes.toBytes(cardTransaction.getAmount())
            );
            put.addColumn(
                Bytes.toBytes("transaction_details"),
                Bytes.toBytes("member_id"),
                Bytes.toBytes(cardTransaction.getMemberId())
            );
            put.addColumn(
                Bytes.toBytes("transaction_details"),
                Bytes.toBytes("pos_id"),
                Bytes.toBytes(cardTransaction.getPosId())
            );
            put.addColumn(
                Bytes.toBytes("transaction_details"),
                Bytes.toBytes("postcode"),
                Bytes.toBytes(cardTransaction.getPostCode())
            );
            put.addColumn(
                Bytes.toBytes("transaction_details"),
                Bytes.toBytes("transaction_dt"),
                Bytes.toBytes(cardTransaction.getTransactionDate())
            );
            put.addColumn(
                Bytes.toBytes("transaction_details"),
                Bytes.toBytes("status"),
                Bytes.toBytes(cardTransaction.getStatus())
            );
            table.put(put);
        } catch (IOException e) {
            throw e;
        } finally {
            table.close();
        }
    }
}
