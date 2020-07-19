package org.upgrad.capstone.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.upgrad.capstone.domain.CardLookup;
import org.upgrad.capstone.domain.CardTransaction;
import org.upgrad.capstone.domain.ZipCodePosIdMap;

import java.io.IOException;
import java.util.Objects;

/**
 * HBase DAO class that helps fetch required data from HBase
 */
public class HBaseDAO {

    final String regex = "\\d+";

    /**
     * Looks up card details in card_lookup table
     * @param cardTransaction
     * @return
     * @throws IOException
     */
    public CardLookup lookupCardDetails(CardTransaction cardTransaction) throws IOException {
        Connection connection = HBaseConnection.getHbaseConnection();
        Table table = null;
        CardLookup cardLookup = new CardLookup();
        try {
            Get cardId = new Get(Bytes.toBytes(cardTransaction.getCardId()));
            table = connection.getTable(TableName.valueOf("card_lookup"));
            Result result = table.get(cardId);
            String postCode = Bytes.toString(result.getValue(Bytes.toBytes("postcode"), Bytes.toBytes("postcode")));
            String ucl = Bytes.toString(result.getValue(Bytes.toBytes("ucl"), Bytes.toBytes("ucl")));
            String transactionDate = Bytes.toString(result.getValue(Bytes.toBytes("postcode"), Bytes.toBytes("transaction_dt")));
            String score = Bytes.toString(result.getValue(Bytes.toBytes("score"), Bytes.toBytes("score")));
            cardLookup.setCardId(cardTransaction.getCardId());
            cardLookup.setUcl(Objects.nonNull(ucl) ? Double.parseDouble(ucl) : 0.0d);
            cardLookup.setPostCode(Objects.nonNull(postCode) ? postCode : null);
            cardLookup.setTransactionDate(Objects.nonNull(transactionDate) && transactionDate.matches(regex) ? Long.parseLong(transactionDate) : 0L);
            cardLookup.setScore(Objects.nonNull(score) ? Integer.parseInt(score) : 0);
        } catch (IOException e) {
            throw e;
        } finally {
            table.close();
        }
        return cardLookup;
    }

    /**
     * Saves card lookup details
     * @param cardLookup
     * @throws IOException
     */
    public void saveCardLookupDetails(CardLookup cardLookup) throws IOException {
        Connection connection = HBaseConnection.getHbaseConnection();
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("card_lookup"));
            Put put = new Put(Bytes.toBytes(cardLookup.getCardId()));
            put.addColumn(
                Bytes.toBytes("postcode"),
                Bytes.toBytes("postcode"),
                Bytes.toBytes(cardLookup.getPostCode())
            );
            put.addColumn(
                Bytes.toBytes("postcode"),
                Bytes.toBytes("transaction_dt"),
                Bytes.toBytes(cardLookup.getTransactionDate())
            );
            table.put(put);
        } catch (IOException e) {
            throw e;
        } finally {
            table.close();
        }
    }

    /**
     * Saves card transaction in card_transaction table
     * @param cardTransaction
     * @throws IOException
     */
    public void saveCardTransaction(CardTransaction cardTransaction) throws IOException {
        Connection connection = HBaseConnection.getHbaseConnection();
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("card_transactions"));
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

    /**
     * Gets zip code details for a particular zip code passed
     * @param zipCode
     * @return
     * @throws IOException
     */
    public ZipCodePosIdMap getZipCodePosIdMap(String zipCode) throws IOException {
        Connection connection = HBaseConnection.getHbaseConnection();
        Table table = null;
        ZipCodePosIdMap zipCodePosIdMap = new ZipCodePosIdMap();
        try {
            Get zipCodeBytes = new Get(Bytes.toBytes(zipCode));
            table = connection.getTable(TableName.valueOf("zip_code_pos_id_map"));
            Result result = table.get(zipCodeBytes);
            String latitude = Bytes.toString(result.getValue(Bytes.toBytes("position_details"), Bytes.toBytes("latitude")));
            String longitude = Bytes.toString(result.getValue(Bytes.toBytes("position_details"), Bytes.toBytes("longitude")));
            String city = Bytes.toString(result.getValue(Bytes.toBytes("position_details"), Bytes.toBytes("city")));
            String stateName = Bytes.toString(result.getValue(Bytes.toBytes("position_details"), Bytes.toBytes("state_name")));
            String posId = Bytes.toString(result.getValue(Bytes.toBytes("position_details"), Bytes.toBytes("pos_id")));
            zipCodePosIdMap.setZipCode(zipCode);
            zipCodePosIdMap.setLatitude(Objects.nonNull(latitude) ? Double.parseDouble(latitude) : 0.0d);
            zipCodePosIdMap.setLongitude(Objects.nonNull(longitude) ? Double.parseDouble(longitude) : 0.0d);
            zipCodePosIdMap.setCity(city);
            zipCodePosIdMap.setStateName(stateName);
            zipCodePosIdMap.setPostId(posId);
        } catch (IOException e) {
            throw e;
        } finally {
            table.close();
        }
        return zipCodePosIdMap;
    }
}
