package com.example.android.yffandroid;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 1/2/17.
 */

public class ArtistRepo {
    private static List<Artist> loadedArtists = new LinkedList<>();

//    private static Artist[] artistsFromApi = {
//            new Artist("1D3D3DD1-3C8D-F828-0E42-D9E82B70ECF8", "Joel Sulman"),
//            new Artist("D7A52587-5AD0-DA40-F401-29130BB4ADC0", "Candice McLeod"),
//            new Artist("E23891A9-AEFA-EBF4-F6AC-79F3E695ED12", "The Kissin Cousins"),
//            new Artist("B03982D8-BA81-3B63-1066-54CCAC1D3970", "Gorani"),
//            new Artist("C1C8B5F3-6622-C48C-5162-B1234803B3A6", "The Bon Scotts"),
//            new Artist("BED5E74D-3438-7BBC-096A-728E350545B3", "Gordie Tentrees"),
//            new Artist("CD283109-F149-5511-7CB2-0DD22C0455E7", "Tom Richardson"),
//            new Artist("67CC1EFB-52BD-726F-F603-4884A13668BB", "True Spirit Revival - Yoga with Live Music"),
//            new Artist("8A3BD8E6-D119-79DD-7CBA-69F343E8A654", "Scott Cook"),
//            new Artist("5E0513E4-A4EE-61D6-76B4-AF5EEE61BA98", "The Jessica Stuart Few"),
//            new Artist("1C7C092D-FBF5-C96C-EE8B-31A20F4B85E4", "The Eastern"),
//            new Artist("9B987AB7-2EAC-CE56-E777-AF771F8BE292", "Black Market Tune"),
//            new Artist("D656A692-1A9E-3D07-4F44-0A694ED64CE3", "Sheila Kay Adams"),
//            new Artist("FA6DA70F-58B2-035A-1B71-50C4334EED4B", "The Northern Folk"),
//            new Artist("E3C891B5-FF57-2966-D6AB-743B5C2836E4", "Fred Smith & Liz Frencham"),
//            new Artist("233B19CC-894A-450D-F8A7-C587BF4AC83D", "Julz Evans"),
//            new Artist("1A7CD6CD-21AE-E28B-9040-466740CA5FBF", "Joel Havea"),
//            new Artist("BF24F831-837D-86FB-13F1-AC1A7EF45725", "Steve Tyson"),
//            new Artist("FA9DF3B8-39E1-2D72-48A4-0F4C2579801D", "Lulu & the Paige-Turners"),
//            new Artist("7C3B1AD1-D240-7842-457C-75FF249F70A7", "The Hannafords"),
//            new Artist("D8EBC445-E55F-D5F8-B2D6-9704E540767F", "Lucy Wise & Stephen Taberner"),
//            new Artist("BC942013-F10B-D1B2-DEA0-9FCED0DD7C5C", "The East Pointers"),
//            new Artist("4F90D063-4AFF-3441-D19C-53ED47E60021", "Whiteline"),
//            new Artist("D70BD8D0-5555-C16C-093E-73EF9DE12D4E", "Frank Yamma"),
//            new Artist("F9AA4442-CDBF-BA5B-0545-EEC75E8D7BDE", "Mick Thomas"),
//            new Artist("D11607FC-DAFD-FCF1-542B-71B17B76F4D7", "Richard Perso"),
//            new Artist("080BFA7C-8A85-A7C9-24F2-250535328EF1", "Pete Denahy"),
//            new Artist("02730E07-72BC-6697-8892-DB87A7C07D69", "Tooze & Bruce"),
//            new Artist("603C1640-048B-2FE3-2ABE-9694841931F5", "Broads"),
//            new Artist("E66EDE6F-0DE6-75B5-CE63-2155A4419E1A", "Jali Buba Kuyateh"),
//            new Artist("3C08937E-38BD-95CE-231E-0F9219D97B94", "Fat Yahoozah"),
//            new Artist("89126053-893F-FFE1-05F5-187BC704840A", "Sheehan Smith & Sheehan"),
//            new Artist("B7F4B9C1-B1D2-B4B6-959E-DAA02D13B4AC", "Anna Cordell"),
//            new Artist("BB51BBDE-785A-B49A-EEE4-9EFF759772C3", "Kirsty Bromley"),
//            new Artist("81EB513A-0FA4-4CA7-F229-DEBA87D6FE8A", "Michaela Alexander"),
//            new Artist("3A5D4D9D-7F61-31F1-DBE4-B49955936A66", "Bleach"),
//            new Artist("109E1AB5-6467-D593-1BBE-A5E5F6B37C8C", "Jarryn Phegan"),
//            new Artist("6DCEA24C-B573-DF58-2D01-0D20C6AF92A7", "Adrian Clark"),
//            new Artist("E3437323-54CF-603E-37E0-D20DDCA029D0", "Arts Yackandandah Community Choir"),
//            new Artist("2B90C113-7002-88E6-1ED1-C452392A9D2D", "Appalachian Heaven String Band"),
//            new Artist("83FCAB1E-2444-B9CA-D171-760D2B02F4FF", "Phil Rourke & Mitch Huon"),
//            new Artist("766FDC57-FA86-7BCD-3E3C-C5C5C0115EA7", "Marisa Quigley"),
//            new Artist("9B45ACB9-9656-080A-EB5A-90350C70CDA5", "Jaxon Haldane")
//    };

    public static List<Artist> getArtists() {
        if (loadedArtists.size() < 1) {
            loadArtists();
        }
        return loadedArtists;
    }

    private static void loadArtists() {
        List<Map<String, String>> artistMaps = new ArtistApiAdapter().getArtistMaps();
        loadedArtists = artistsFromMapList(artistMaps);
    }

    public static List<Artist> artistsFromMapList(List<Map<String, String>> artistMaps) {
        List<Artist> artists = new LinkedList<>();

        for (Map artistMap : artistMaps) {
            String id = artistMap.get("id").toString();
            String name = artistMap.get("name").toString();

            Artist newArtist = new Artist(id, name);
            artists.add(newArtist);
        }

        return artists;
    }
}
