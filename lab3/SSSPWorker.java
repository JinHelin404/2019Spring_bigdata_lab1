package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class SSSPWorker extends Worker<Integer,Integer>{
	public SSSPWorker(int id) {
		super(id);
	}
	
	@Override
	public void init(){
		last_msg = new HashMap<Integer, Queue<Message>>();
		for(Vertex<Integer,Integer> v:vertexes){
			last_msg.put(v.getId(), new LinkedBlockingQueue<Message>());
		}
		current_msg = new HashMap<Integer, Queue<Message>>();
		for(Vertex<Integer,Integer> v:vertexes){
			current_msg.put(v.getId(), new LinkedBlockingQueue<Message>());
		}
		if(last_msg.containsKey(0)){
			Message m = new Message();
			m.ssspvalue = 0;
			last_msg.get(0).add(m);
		}
		send_queue = new HashMap<Integer, Queue<Message>>();
	}

	@Override
	public void load(){
		String path = "part " + this.id;
		FileReader fr;
		try {
			fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			int size_vertex = Integer.parseInt(br.readLine());
			for(int i=0;i<size_vertex;i++){
				int id = Integer.parseInt(br.readLine());
				SSSPVertex v = new SSSPVertex(id);
				v.setValue(INF);
				this.vertexes.add(v);
				this.edges.put(id, new ArrayList<Edge<Integer>>());
			}
			int size_edge = Integer.parseInt(br.readLine());
			for(int i=0;i<size_edge;i++){
				String line = br.readLine();
				//System.out.println(line);
				String[] words = line.split("\t");
				this.edges.get(Integer.parseInt(words[0])).add(new Edge<Integer>(1,Integer.parseInt(words[1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
