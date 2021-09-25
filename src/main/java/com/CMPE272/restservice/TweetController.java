package com.CMPE272.restservice;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serdar Demirci
 */
@RestController
public class TweetController {

    @GetMapping("/tweet")
    Tweet readTweet(@RequestParam String id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // Twitter Access url & Authorization Bearer
        Request request = new Request.Builder()
                .url("https://api.twitter.com/2/tweets/" + id + "?tweet.fields=created_at,text")
                .method("GET", null)
                .addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAALTFTQEAAAAA8KnV5jqnjuYcZRQ8yCV8ygdoVEk%3Dnu0GtT58tXNqIRZZAnb6KGWmmntPFLRaUbtHyzNBtboHGFrSGq")
                .build();

        // Get a Tweet using Twitter v2 API
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseStr = response.body().string();
            Data data = new Gson().fromJson(responseStr, Data.class);
            if (data.getErrors() == null) {
                return data.tweet;
            } else {
                throw new ResourceNotFoundException();
            }
        } else {
            throw new GenericException();
        }
    }

    @DeleteMapping("/tweet")
    Tweet deleteTweet(@RequestParam String id) {
        Twitter twitter = new TwitterFactory().getInstance();

        // Twitter Consumer key & Consumer Secret
        twitter.setOAuthConsumer("mYKrRdA5ddEpmocEM1IANTQBY", "7gfhqWpdenH4q1GXq4Hxjczra41j8zdFn8QhS1lBUf0Lh6wXFv");

        // Twitter Access token & Access token Secret
        twitter.setOAuthAccessToken(new AccessToken("1434977924306800640-QnmX8xVK918mDjYMUk2Wezciud65r7",
                "JvP8GUapOkkFXCSWb3TQYw7YFkjm99VPiyl5mDdwdOZnR"));

        try {
            // Delete a Tweet using Twitter4j API
            Status status = twitter.destroyStatus(Long.parseLong(id));
            return new Tweet(status.getCreatedAt().toString(), String.valueOf(status.getId()), status.getText());
        } catch (TwitterException e) {
            if (e.getErrorCode() == 144) {
                throw new ResourceNotFoundException();
            } else {
                throw new GenericException();
            }
        }
    }

    @PostMapping("/tweet")
    Tweet createTweet(@RequestParam String text) {
        Twitter twitter = new TwitterFactory().getInstance();

        // Twitter Consumer key & Consumer Secret
        twitter.setOAuthConsumer("mYKrRdA5ddEpmocEM1IANTQBY", "7gfhqWpdenH4q1GXq4Hxjczra41j8zdFn8QhS1lBUf0Lh6wXFv");

        // Twitter Access token & Access token Secret
        twitter.setOAuthAccessToken(new AccessToken("1434977924306800640-QnmX8xVK918mDjYMUk2Wezciud65r7",
                "JvP8GUapOkkFXCSWb3TQYw7YFkjm99VPiyl5mDdwdOZnR"));

        try {
            // Create a Tweet using Twitter4j API
            Status status = twitter.updateStatus(text);
            return new Tweet(status.getCreatedAt().toString(), String.valueOf(status.getId()), status.getText());
        } catch (TwitterException e) {
            throw new GenericException();
        }
    }

    @GetMapping("/timeline")
    List<Tweet> listTimeline() {
        Twitter twitter = new TwitterFactory().getInstance();

        // Twitter Consumer key & Consumer Secret
        twitter.setOAuthConsumer("mYKrRdA5ddEpmocEM1IANTQBY", "7gfhqWpdenH4q1GXq4Hxjczra41j8zdFn8QhS1lBUf0Lh6wXFv");

        // Twitter Access token & Access token Secret
        twitter.setOAuthAccessToken(new AccessToken("1434977924306800640-QnmX8xVK918mDjYMUk2Wezciud65r7",
                "JvP8GUapOkkFXCSWb3TQYw7YFkjm99VPiyl5mDdwdOZnR"));

        List<Tweet> tweets = new ArrayList<>();

        try {
            // Get 10 Tweets using Twitter4j API
            ResponseList<Status> statusReponseList = twitter.getUserTimeline(new Paging(1, 10));
            for (Status status : statusReponseList) {
                Tweet tweet = new Tweet(status.getCreatedAt().toString(), String.valueOf(status.getId()), status.getText());
                tweets.add(tweet);
            }
        } catch (TwitterException e) {
            throw new GenericException();
        }

        return tweets;
    }

    class Data {
        @SerializedName("data")
        private Tweet tweet;

        private List<Error> errors;

        public Tweet getTweet() {
            return tweet;
        }

        public List<Error> getErrors() {
            return errors;
        }
    }

    class Error {
        private String value, detail, title;
    }

    class Tweet {
        @SerializedName("created_at")
        private String createdAt;
        private String id;
        private String text;

        public Tweet() {
        }

        public Tweet(String createdAt, String id, String text) {
            this.createdAt = createdAt;
            this.id = id;
            this.text = text;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getId() {
            return id;
        }

        public String getText() {
            return text;
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public class GenericException extends RuntimeException {
    }
}
