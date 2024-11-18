import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@KafkaListener(topics = "user-posts", groupId = "feed-workers")
public class FeedFanoutWorker {

    //@Autowired
    //private UserService userService; // Fetch followers

    @Autowired
    private FeedRepository feedRepository; // Save updates to followers' feeds

    public void handlePost(ConsumerRecord<String, String> record) {
        String userId = record.key();
        String postContent = record.value();

        List<String> followers = userService.getFollowers(userId);

        followers.forEach(followerId -> {
            feedRepository.saveToFeed(followerId, postContent);
        });
    }
}
