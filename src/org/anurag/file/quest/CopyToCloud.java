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

package org.anurag.file.quest;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author Anurag....
 *
 */
public class CopyToCloud implements OnClickListener {

	private Item item;
	
	public CopyToCloud(Context ctx , Item itm) {
		// TODO Auto-generated constructor stub
		this.item = itm;
		
		Dialog dialog = new Dialog(ctx, R.style.custom_dialog_theme);
		dialog.setContentView(R.layout.copy_to_cloud);
		dialog.getWindow().getAttributes().width = FileQuest.size.x*8/9;
		
		
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
