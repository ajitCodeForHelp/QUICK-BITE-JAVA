package com.quickBite.primary.repository;

import com.quickBite.primary.pojo.PrimarySequenceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PrimarySequenceRepository {

    @Autowired
    private MongoOperations mongoOperation;

    public Long getNextSequenceId(String key) {

        try {
            //get sequence id
            Query query = new Query(Criteria.where("_id").is(key));

            //increase sequence id by 1
            Update update = new Update();
            update.inc("seq", 1);

            //return new increased id
            FindAndModifyOptions options = new FindAndModifyOptions();
            options.returnNew(true);
            options.upsert(true);

            //this is the magic happened.
            PrimarySequenceId seqId = mongoOperation.findAndModify(query, update, options, PrimarySequenceId.class);

            //if no id, throws SequenceException
            //optional, just a way to tell user when the sequence id is failed to generate.

            if (seqId == null) {
                return null;
            }
            return seqId.getSeq();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}