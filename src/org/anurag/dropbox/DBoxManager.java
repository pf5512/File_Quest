/**
 * Copyright(c) 2014 DRAWNZER.ORG PROJECTS -> ANURAG
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *                             
 *                             anurag.dev1512@gmail.com
 *
 */

package org.anurag.dropbox;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;


/**
 * 
 * @author Anurag
 *
 */
public class DBoxManager {
	
	public static boolean DBOX_ROOT = false;
	public static boolean DBOX_SIMPLE = false;
    public static String rootPath = "/";
    public static String simplePath = "/";

    
    /**
     * 
     * @return
     */
    public static ArrayList<DBoxObj> generateListForRoot(Context ctx){
    	DropboxAPI<?> api = DBoxAuth.mApi;
    	try {
    		com.dropbox.client2.DropboxAPI.Entry list = api.metadata(rootPath, 1000, "", true, null);
			if(list.isDir){
				ArrayList<DBoxObj> mainList = new ArrayList<DBoxObj>();
				List<com.dropbox.client2.DropboxAPI.Entry> entries = list.contents;
				for(Entry ent:entries)
					mainList.add(new DBoxObj(ent,ctx));
				return mainList;
			}
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<DBoxObj>();
		}
    	return new ArrayList<DBoxObj>();
    }
}