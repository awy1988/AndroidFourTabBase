package com.demo.corelib;

import com.demo.corelib.testbean.Birthday;
import com.demo.corelib.testbean.Contacts;
import com.demo.corelib.testbean.User;
import com.demo.corelib.util.JsonUtils;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void jsonUtil_test_json_string_to_bean() {



        String jsonStr1 = "{"
            + "    \"name\":\"UserName\","
            + "    \"password\":\"password\","
            + "    \"contacts\": {"
            + "        \"phone\":\"13255558888\","
            + "        \"location\":\"大连市甘井子区\""
            + "    }"
            + "}";


        User user1 = JsonUtils.parseObject(jsonStr1, User.class);

        assertThat(user1.getName(), is("UserName"));
        assertThat(user1.getPassword(), is("password"));
        assertThat(user1.getContacts().getPhone(), is("13255558888"));
        assertThat(user1.getContacts().getLocation(), is("大连市甘井子区"));

    }

    @Test
    public void jsonUtil_test_bean_to_json_string() {

        User user = new User();
        user.setName("UserName");
        user.setPassword("password");
        Contacts contacts = new Contacts();
        contacts.setPhone("13255558888");
        contacts.setLocation("大连市甘井子区");
        user.setContacts(contacts);

        String jsonStr1 = "{\"name\":\"UserName\",\"password\":\"password\",\"contacts\":{\"phone\":\"13255558888\",\"location\":\"大连市甘井子区\"}}";

        String jsonStr2 = JsonUtils.toJsonString(user);

        assertEquals(jsonStr1, jsonStr2);

    }

    @Test
    public void jsonUtil_test_bean_list_to_json_string() {

        List<User> users = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setName("UserName" + i);
            user.setPassword("password" + i);
            Contacts contacts = new Contacts();
            contacts.setPhone("13255558888" + i);
            contacts.setLocation("大连市甘井子区" + i);
            user.setContacts(contacts);
            users.add(user);
        }

        String jsonStr1 = "["
            + "{\"name\":\"UserName0\",\"password\":\"password0\",\"contacts\":{\"phone\":\"132555588880\",\"location\":\"大连市甘井子区0\"}},"
            + "{\"name\":\"UserName1\",\"password\":\"password1\",\"contacts\":{\"phone\":\"132555588881\",\"location\":\"大连市甘井子区1\"}},"
            + "{\"name\":\"UserName2\",\"password\":\"password2\",\"contacts\":{\"phone\":\"132555588882\",\"location\":\"大连市甘井子区2\"}}"
            + "]";
        String jsonStr2 = JsonUtils.toJsonString(users);

        assertEquals(jsonStr1, jsonStr2);

    }

    @Test
    public void jsonUtil_test_json_string_to_bean_list() {
        String jsonStr1 = "["
            + "{\"name\":\"UserName0\",\"password\":\"password0\",\"contacts\":{\"phone\":\"132555588880\",\"location\":\"大连市甘井子区0\"}},"
            + "{\"name\":\"UserName1\",\"password\":\"password1\",\"contacts\":{\"phone\":\"132555588881\",\"location\":\"大连市甘井子区1\"}},"
            + "{\"name\":\"UserName2\",\"password\":\"password2\",\"contacts\":{\"phone\":\"132555588882\",\"location\":\"大连市甘井子区2\"}}"
            + "]";
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setName("UserName" + i);
            user.setPassword("password" + i);
            Contacts contacts = new Contacts();
            contacts.setPhone("13255558888" + i);
            contacts.setLocation("大连市甘井子区" + i);
            user.setContacts(contacts);
            users.add(user);
        }

        List<User> users1 = JsonUtils.parseArray(jsonStr1, User.class);

        for (int i = 0; i < 3; i++) {
            User user = users1.get(i);
            assertThat(user.getName(), is("UserName" + i));
            assertThat(user.getPassword(), is("password" + i));
            assertThat(user.getContacts().getPhone(), is("13255558888" + i));
            assertThat(user.getContacts().getLocation(), is("大连市甘井子区" + i));
        }
    }

    @Test
    public void jsonUtil_date_to_json_string() {
        // 日期类型转 JSON 测试
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 7, 6, 13, 35, 33);
        Date date = calendar.getTime();

        Birthday birthday = new Birthday();
        birthday.setBirthday(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

        String jsonStr = JsonUtils.toJsonString(birthday, dateFormat);

        assertThat(jsonStr, is("{\"birthday\":\"2020-08-06 13:35:33\"}"));
    }

    @Test
    public void jsonUtil_json_string_to_date() throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 7, 6, 13, 35, 33);
        Date date = calendar.getTime();

        Birthday birthday = new Birthday();
        birthday.setBirthday(date);

        StdDateFormat dateFormat1 = new StdDateFormat().withColonInTimeZone(true);
        String result = JsonUtils.toJsonString(birthday, dateFormat1);

        System.out.println(result);

        Birthday birthday1 = JsonUtils.parseObject(result, Birthday.class);

        assertThat(birthday1.getBirthday(), is(date));

    }



}
