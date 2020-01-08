package devutility.external.mongo.internal;

import java.util.Map;

import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import devutility.external.mongo.BaseTest;
import devutility.internal.test.TestExecutor;

public class QueryTest extends BaseTest {
	@Override
	public void run() {
		Criteria criteria = new Criteria();
		criteria.orOperator(Criteria.where("_id").is("asd"), Criteria.where("_id").is("qwe"));

		Query query = new Query();
		query.addCriteria(criteria);

		println(query.toString());
		println(query.getQueryObject().toJson());

		queryObject(query);
		queryObject(Query.query(Criteria.where("_id").is("asd")));
	}

	void queryObject(Query query) {
		Document document = query.getQueryObject();
		println(String.format("Document empty: %b", document.isEmpty()));
		println(String.format("Document size: %d", document.size()));

		for (Map.Entry<String, Object> queryEntry : document.entrySet()) {
			println(queryEntry.getKey() + ": " + queryEntry.getValue().toString());
		}
	}

	public static void main(String[] args) {
		TestExecutor.run(QueryTest.class);
	}
}