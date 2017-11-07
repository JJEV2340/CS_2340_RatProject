package org.lulz.jrat;

import android.os.Parcel;
import com.google.firebase.firestore.GeoPoint;
import org.junit.Test;
import org.lulz.jrat.model.impl.RatSighting;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Tests RatSighting.writeToParcel()
 */
public class RatSightingParcelTest {

    @Test
    public void testWriteToParcel() throws Exception {
        Date date = new Date();
        GeoPoint loc = new GeoPoint(42, -42);
        String locType = "dont no";
        String zip = "11111";
        String addr = "420 blaze it";
        String city = "new york";
        String borough = "compton";
        RatSighting ratSighting = new RatSighting(
                date, loc, locType, zip, addr, city, borough
        );
        Parcel parcel = MockParcel.obtain();
        ratSighting.writeToParcel(parcel, 0);

        assertEquals(parcel.readLong(), date.getTime());
        assertEquals(parcel.readDouble(), loc.getLatitude(), 0);
        assertEquals(parcel.readDouble(), loc.getLongitude(), 0);
        assertEquals(parcel.readString(), locType);
        assertEquals(parcel.readString(), zip);
        assertEquals(parcel.readString(), addr);
        assertEquals(parcel.readString(), city);
        assertEquals(parcel.readString(), borough);
    }

    @Test
    public void testWriteToParcelNull() throws Exception {
        Date date = null;
        GeoPoint loc = null;
        String locType = "dont no";
        String zip = "11111";
        String addr = "420 blaze it";
        String city = "new york";
        String borough = "compton";
        RatSighting ratSighting = new RatSighting(
                date, loc, locType, zip, addr, city, borough
        );
        Parcel parcel = MockParcel.obtain();
        ratSighting.writeToParcel(parcel, 0);

        assertEquals(parcel.readLong(), 0);
        assertEquals(parcel.readDouble(), 0, 0);
        assertEquals(parcel.readDouble(), 0, 0);
        assertEquals(parcel.readString(), locType);
        assertEquals(parcel.readString(), zip);
        assertEquals(parcel.readString(), addr);
        assertEquals(parcel.readString(), city);
        assertEquals(parcel.readString(), borough);
    }
}
