/*
 * Copyright (c) 2018, Monaco (monaco <at> muneyamaru <dot> net)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.muneyamaru.weebget;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class KonachanRetriever implements Retriever {
    private static final String BASE = "http://konachan.com/";
    
    @Override
    public List<ImageMeta> getAllImages(String tags) {
        List<ImageMeta> ret = new ArrayList<>();
        JSONParser parser = new JSONParser();
        // TODO properly process tags?
        for(int page = 1; true; page++) {
            try {
                URL url = new URL(BASE + "post.json?limit=100&tags=" + tags + "&page=" + page);
                byte[] data = Utility.readStream(url.openStream());
                JSONArray json = (JSONArray)parser.parse(new String(data));
                if(json.isEmpty()) break;
                json.stream().forEach((Object o) -> {
                    ret.add(new ImageMeta((JSONObject)o));
                });
            } catch(Exception ex) {
                // TODO specify, handle
                ex.printStackTrace();
                break;
            }
            
        }
        return ret;
        
        
    }
    
}
