import com.sergeykotov.testcasemanager.model.Group;
import com.sergeykotov.testcasemanager.model.Story;
import com.sergeykotov.testcasemanager.model.Tester;
import com.sergeykotov.testcasemanager.service.GroupService;
import com.sergeykotov.testcasemanager.service.StoryService;
import com.sergeykotov.testcasemanager.service.TesterService;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public final class DbServicesTest {
    @Test
    public void run() {
        List<Tester> testers = TesterService.getAll();
        Assert.assertNotNull(testers);
        int testerCount = testers.size();
        TesterService.create("test");
        testers = TesterService.getAll();
        Assert.assertTrue(testers != null && testers.size() == testerCount + 1);

        Long testerId = testers.get(0).getId();
        TesterService.update(testerId, "test (updated)");
        Tester tester = TesterService.get(testerId);
        Assert.assertNotNull(tester);
        Assert.assertEquals("test (updated)", tester.getName());

        List<Story> stories = StoryService.getAll();
        Assert.assertNotNull(stories);
        int storyCount = stories.size();
        StoryService.create(Integer.MAX_VALUE, LocalDate.ofEpochDay(1));
        stories = StoryService.getAll();
        Assert.assertTrue(stories != null && stories.size() == storyCount + 1);

        Long storyId = stories.get(0).getId();
        StoryService.update(storyId, Integer.MAX_VALUE - 1, LocalDate.ofEpochDay(1));
        Story story = StoryService.get(storyId);
        Assert.assertNotNull(story);
        Assert.assertEquals(Integer.MAX_VALUE - 1, story.getNum());

        List<Group> groups = GroupService.getAll();
        Assert.assertNotNull(groups);
        int groupCount = groups.size();
        GroupService.create(storyId, null, 1, "test");
        groups = GroupService.getAll();
        Assert.assertTrue(groups != null && groups.size() == groupCount + 1);

        Long groupId = groups.get(0).getId();
        GroupService.update(groupId, storyId, null, 1, "test (updated)");
        Group group = GroupService.get(groupId);
        Assert.assertNotNull(group);
        Assert.assertEquals("test (updated)", group.getName());

        GroupService.delete(groupId, group.getName());
        groups = GroupService.getAll();
        Assert.assertTrue(groups != null && groups.size() == groupCount);

        TesterService.delete(testerId, tester.getName());
        testers = TesterService.getAll();
        Assert.assertTrue(testers != null && testers.size() == testerCount + 1);

        StoryService.delete(storyId, story.toString());
        stories = StoryService.getAll();
        Assert.assertTrue(stories != null && stories.size() == storyCount);
    }
}
