package com.devicehive;

import com.devicehive.auth.CheckPermissionsHelper;
import com.devicehive.model.AccessKeyPermission;
import com.devicehive.model.JsonStringWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
public class AccessKeyPermissionsSubnetsTest {

    @Test
    public void subnetsCleanPermissionsTest() {
        Set<AccessKeyPermission> permissions = new HashSet<>();

        AccessKeyPermission permission1 = new AccessKeyPermission();
        permission1.setSubnets(new JsonStringWrapper("[\"203.0.113.0/24\",\"127.0.0.1\"]"));

        AccessKeyPermission permission2 = new AccessKeyPermission();
        permission2.setSubnets(new JsonStringWrapper("[]"));

        AccessKeyPermission permission3 = new AccessKeyPermission();

        permissions.add(permission1);
        permissions.add(permission2);
        permissions.add(permission3);
        boolean result = false;
        try {
            result = CheckPermissionsHelper.checkIP(InetAddress.getByName("203.0.113.7"), permissions);
        } catch (UnknownHostException e) {
            fail("No exceptions expected!");
        }
        assertTrue(result);
        assertEquals(2, permissions.size());
    }

    @Test
    public void domainsEmptyPermissionsCase() {
        Set<AccessKeyPermission> permissions = new HashSet<>();
        boolean result = true;
        try {
            result = CheckPermissionsHelper.checkIP(InetAddress.getByName("203.0.113.7"), permissions);
        } catch (UnknownHostException e) {
            fail("No exceptions expected!");
        }
        assertFalse(result);
    }

    @Test
    public void hasNoAccessToSubnetsOnePermissionSuccessTest() {
        Set<AccessKeyPermission> permissions = new HashSet<>();
        AccessKeyPermission singlePermission = new AccessKeyPermission();
        singlePermission.setSubnets(new JsonStringWrapper("[\"127.0.0.1\", \"203.1.113.7/16\"]"));
        permissions.add(singlePermission);
        boolean result = true;
        try {
            result = CheckPermissionsHelper.checkIP(InetAddress.getByName("203.0.113.7"), permissions);
        } catch (UnknownHostException e) {
            fail("No exceptions expected!");
        }
        assertFalse(result);
        assertEquals(0, permissions.size());
    }

    @Test
    public void hasAccessToSubnetsSeveralPermissionsTest() {
        Set<AccessKeyPermission> permissions = new HashSet<>();

        AccessKeyPermission permission1 = new AccessKeyPermission();
        permission1.setSubnets(new JsonStringWrapper("[\"127.0.0.0/8\", \"113.14.8.9\"]"));
        permissions.add(permission1);

        AccessKeyPermission permission2 = new AccessKeyPermission();
        permission2.setSubnets(new JsonStringWrapper("[]"));
        permissions.add(permission2);

        AccessKeyPermission permission3 = new AccessKeyPermission();
        permission3.setSubnets(new JsonStringWrapper("[\"203.0.113.7\"]"));
        permissions.add(permission3);

        AccessKeyPermission permission4 = new AccessKeyPermission();
        permission4.setSubnets(new JsonStringWrapper("[\"203.0.116.7/13\"]"));
        permissions.add(permission4);

        AccessKeyPermission permission5 = new AccessKeyPermission();
        permissions.add(permission5);

        boolean result = false;
        try {
            result = CheckPermissionsHelper.checkIP(InetAddress.getByName("203.0.113.7"), permissions);
        } catch (UnknownHostException e) {
            fail("No exceptions expected!");
        }
        assertTrue(result);
        assertEquals(3, permissions.size());
    }

    @Test
    public void hasNoAccessToDomainSeveralPermissionsTest() {
        Set<AccessKeyPermission> permissions = new HashSet<>();

        AccessKeyPermission permission1 = new AccessKeyPermission();
        permission1.setSubnets(new JsonStringWrapper("[\"203.111.6.7/13\"]"));
        permissions.add(permission1);

        AccessKeyPermission permission2 = new AccessKeyPermission();
        permission2.setSubnets(new JsonStringWrapper("[]"));
        permissions.add(permission2);

        AccessKeyPermission permission3 = new AccessKeyPermission();
        permission3.setSubnets(new JsonStringWrapper("[\"203.0.113.8/32\"]"));
        permissions.add(permission3);

        boolean result = true;
        try {
            result = CheckPermissionsHelper.checkIP(InetAddress.getByName("203.0.113.7"), permissions);
        } catch (UnknownHostException e) {
            fail("No exceptions expected!");
        }
        assertFalse(result);
        assertEquals(0, permissions.size());
    }
}
