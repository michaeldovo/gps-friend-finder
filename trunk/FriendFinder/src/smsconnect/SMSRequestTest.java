/*
 * SMSRequestTest.java
 * JMUnit based test
 *
 * Created on 28.05.2008, 23:34:56
 */

package smsconnect;


import java.io.IOException;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import smsconnect.SMSRequest;
import jmunit.framework.cldc10.*;
import main.Property;

/**
 * @author Chris2u
 */
public class SMSRequestTest extends TestCase {
    
    public SMSRequestTest() {
        //The first parameter of inherited constructor is the number of test cases
        super(1,"SMSRequestTest");
    }            

    public void test(int testNumber) throws Throwable {
        switch (testNumber) {
            case 0:
                testDecode();
                break;
            case 1:
                testSetPhoneNumber();
                break;
            case 2:
                testGetPhoneNumber();
                break;
            case 3:
                testSend();
                break;
            case 4:
                testGetSessionId();
                break;
            default:
                break;
        }
    }

    /**
     * Test of testDecode method, of class SMSRequest.
     */
    public void testDecode() throws AssertionFailedException, PIMException, IOException {
        System.out.println("decode");
          ContactList addressbook = (ContactList) (PIM.getInstance().openPIMList(
                PIM.CONTACT_LIST, PIM.READ_ONLY));
        
        String inputSession = "12345";
        SMSRequest r = new SMSRequest((Contact) addressbook.items().nextElement(), inputSession);
        String msg = r.send();
        

        String expResult_1 = inputSession;
        String result_1 = SMSRequest.decode(msg).getSessionId();
        assertEquals(expResult_1, result_1);
    }

    /**
     * Test of testSetPhoneNumber method, of class SMSRequest.
     */
    public void testSetPhoneNumber() throws AssertionFailedException {
        System.out.println("setPhoneNumber");
        SMSRequest instance = null;
        String number_1 = "";
        instance.setPhoneNumber(number_1);
        fail("The test case is a prototype.");
    }

    /**
     * Test of testGetPhoneNumber method, of class SMSRequest.
     */
    public void testGetPhoneNumber() throws AssertionFailedException {
        System.out.println("getPhoneNumber");
        SMSRequest instance = null;
        String expResult_1 = "";
        String result_1 = instance.getPhoneNumber();
        assertEquals(expResult_1, result_1);
        fail("The test case is a prototype.");
    }

    /**
     * Test of testSend method, of class SMSRequest.
     */
    public void testSend() throws AssertionFailedException, Exception {
        System.out.println("send");
        SMSRequest instance = null;
        String expResult_1 = "";
        String result_1 = instance.send();
        assertEquals(expResult_1, result_1);
        fail("The test case is a prototype.");
    }

    /**
     * Test of testGetSessionId method, of class SMSRequest.
     */
    public void testGetSessionId() throws AssertionFailedException {
        System.out.println("getSessionId");
        SMSRequest instance = null;
        String expResult_1 = "";
        String result_1 = instance.getSessionId();
        assertEquals(expResult_1, result_1);
        fail("The test case is a prototype.");
    }
}
