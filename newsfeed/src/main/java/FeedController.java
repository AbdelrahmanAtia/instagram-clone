import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedRepository feedRepository;

    @GetMapping("/{userId}")
    public List<String> getFeed(@PathVariable String userId) {
        return feedRepository.getUserFeed(userId);
    }
}