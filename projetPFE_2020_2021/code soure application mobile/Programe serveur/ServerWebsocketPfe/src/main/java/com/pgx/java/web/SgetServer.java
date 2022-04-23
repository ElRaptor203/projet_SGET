package com.pgx.java.web;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.tomcat.util.codec.binary.Base64;

@ServerEndpoint("/endpoint")
public class SgetServer {
    
    Connection connection = null;
	static int countId = 0;
	String message1;
	String message2;
	String message3;
	String message4;
	String message5;
	String message6;
	String message7;
	String message8;
	String message9;
	String message10;
	String message0;
	String messageN = "";
	boolean start = false;
	
    @OnOpen
    public void onOpen(Session session) throws SQLException {
    	connectdatabase();

        System.out.println("onOpen::" + session.getId());        
    }
    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" +  session.getId());
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws SQLException, IOException {
    	
    	
    	if(!message.equals("Hello World!")) {
    		
    		if(!start) {
    			message0 = message.substring(0,message.indexOf("@"));
    			if(message0.length()<20) {
    				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            	    System.out.println("@@@@@@@@@@ le contenue du message es : "+message0+"@@@@@@@@@@@@@");
            	    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    			}
    		
    		}
    		
			if(message0.equals("taille")) {
				 message1 = message.substring(message0.length()+1,message.indexOf("&"));
		    		message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));		
				 String nbe = "";
           	  System.out.println("ok suis dans debut ");
       		String sql = "SELECT COUNT(*) FROM raptor.planning_emploie_de_temps";
 				Statement statement3w = connection.createStatement();
 				ResultSet result3 = statement3w.executeQuery(sql); 				
 				while(result3.next()) {
 					 nbe = result3.getString("count");
             	  System.out.println("nombre d'article : "+nbe);
 				}
           	  System.out.println("taille de la liste : "+message1);
           	  
           	  if(nbe.equals(message1)) {
                 	  System.out.println("nombre d'article es egale...");
           	  } else {
                 	  System.out.println("nombre d'article n'es pas egale...");
                 	 System.out.println("envoie des donnees au client en cour...");
             		senDataToClient(session,message2);
             		System.out.println("envoie des donnees terminer avec sucess. ok");
           	  }
			 }
    		
			if(message0.equals("inscription")) {
				message1 = message.substring(message0.length()+1,message.indexOf("&"));
	    		message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));
	    		message3 = message.substring(message2.length()+message1.length()+message0.length()+3,message.indexOf("$"));
	    		message4 = message.substring(message3.length()+message1.length()+message2.length()+message0.length()+4,message.indexOf("%"));
	    		message5 = message.substring(message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+5,message.indexOf("¼"));
	    		message6 = message.substring(message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+6,message.indexOf("½"));
	    		
	    		   System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
	               System.out.println("onMessage::From=" + session.getId() + " message1=" + message1);
	               System.out.println("onMessage::From=" + session.getId() + " message2=" + message2);
	               System.out.println("onMessage::From=" + session.getId() + " message3=" + message3);
	               System.out.println("onMessage::From=" + session.getId() + " message4=" + message4);
	               System.out.println("onMessage::From=" + session.getId() + " Message=" + message5);
	               System.out.println("onMessage::From=" + session.getId() + " message1=" + message6);
	    		
	               insertInscription(message1,message2,message3,message4,message5,message6);

			 }
			
			if(message0.equals("connection")) {
			    			
				message1 = message.substring(message0.length()+1,message.indexOf("&"));
	    		message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));
				////////////////////////////
	    		String data7 = null ;
				String data8 = "";
		    	        System.out.println(" suis dans connection... ");
		          
		                  System.out.println("message1 : "+message1);
		                  System.out.println("message2 : "+message2);
		                  
			                 String sqlcn = "SELECT * FROM raptor.user WHERE login = '"+message1+"'";
			  				Statement statementcn1 = connection.createStatement();
			  				ResultSet resultcn1 = statementcn1.executeQuery(sqlcn);
			    	        Boolean pret = resultcn1.next();
			    	        System.out.println("resultats : "+pret);
			  				if(pret) {
			  					 data7 = resultcn1.getString("login");
			  					 data8 = resultcn1.getString("password");
			    	    	        System.out.println("login : "+data7);
			       	    	        System.out.println("password : "+data8);
			       	    	    if(data7.equals(message1) && data8.equals(message2) ) {
					    	        System.out.println("connection reusie");
					    	        String sql351 = "SELECT * FROM raptor.user WHERE login = '"+message1+"'";
					  				Statement statement351 = connection.createStatement();
					  				ResultSet result351 = statement351.executeQuery(sql351); 
					  				while (result351.next()) {
				  	  					
					  	  				String data11 = result351.getString("nom");
										String data21 = result351.getString("prenom");
										String data12 = result351.getString("satus");
										String data22 = result351.getString("photo_profile");		
										String data23 = result351.getString("matricule");		
						    	        System.out.println("matricule reussiteconnection : "+data23);

						 						try {
													 getTime(250);
													 session.getBasicRemote().sendText("reussiteconnection"+"&"+data11+"#"+data21+"%"+data12+"$"+data22+"^"+data23+"¼"+getFiliere(data23)+"½"+getSemaine(data23)+"~");
										           } catch (IOException e) {
										                e.printStackTrace();
										            }
						 					 
					  	  				}

			       	    	    } else {
					    	        System.out.println("login ou mots de passe incorecte");
									 session.getBasicRemote().sendText("echecconnection"+"&");

			       	    	    }
			       	    	    	
			  				}else {
								 session.getBasicRemote().sendText("usernoexit"+"&");
				    	        System.out.println("cet utilisateur néxiste pas");
			  					data7 = "";
			  	  				data8 = "";
			  				}
		        }
		        
				
				
				/////////////////////////
				
			   // senDataToClient(session);
			}
			    		
			
    	if(message0.equals("savereservation")) {
    		String data1 = "";
			message1 = message.substring(message0.length()+1,message.indexOf("&"));
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	    System.out.println("+++++++++ le contenue du message es : "+message1+"++++++++++++++");
    	    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			String sql = "SELECT * FROM voiture  WHERE image = '"+message1+"'";
	    	
	    	try {
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(sql);
			
	            System.out.println("debut de la recuperation des donne de la bd...");
				while(result.next()) {
					 data1 = result.getString("id_voiture");
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("fin de la recuperation des donne de la bd...");
			
		      final String INSERT_PRODUCT_SQL2 = "INSERT INTO reservation" +
		            "  (id_voiture,etat) VALUES " +
		            " (?, ?);";
		    System.out.println(INSERT_PRODUCT_SQL2);
            // Step 1: Establishing a Connection

                // Step 2:Create a statement using connection object
                
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL2);
                
                preparedStatement.setString(1, data1);
                preparedStatement.setString(2, "non louer");
          
                
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            
		}
		
    	
    		if(message0.equals("AccueilFragment")) {
    			
    			//senDataToClient(session);
    		}
    		
    		
			if(message0.equals("AccueilFragment")) {
			    			
			    			//senDataToClient(session);
    		}
    		
    		
			if(message0.equals("etatsmatiere")) {
				
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();        
				String result = "Date is 11/07/2021 19:19:30";
				String dateToStr = dateFormat.format(date);
				System.out.println("Date is "+ dateToStr);
			    			
				session.getBasicRemote().sendText("verifieretatsmatiere"+"@"+dateToStr+"&");
			}
    		
    		
			if(message0.equals("DashboardFragment5")) {
				message1 = message.substring(message0.length()+1,message.indexOf("&"));
	    		message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));
	          	  System.out.println("DashboardFragment5 matricule : "+message1);
	          	  System.out.println("taille de la liste : "+message2);

		    		String nbe = "";
		    		String libeler = "";
		    		String nbrsemaine = "";
          	  System.out.println("ok suis dans debut ");
      		String sql = "SELECT COUNT(*) FROM raptor.planning_emploie_de_temps";
				Statement statement3w = connection.createStatement();
				ResultSet result3 = statement3w.executeQuery(sql); 				
				while(result3.next()) {
					 nbe = result3.getString("count");
	            	  System.out.println("nombre d'article : "+nbe);
				}
          	  
          	  if(nbe.equals(message2)) {
                	  System.out.println("nombre d'article es egale...");
          	  } else {
                	  System.out.println("nombre d'article n'es pas egale...");
                	 System.out.println("envoie des donnees au client en cour...");
                	 
               		String sqls = "SELECT * FROM raptor.planning_emploie_de_temps where filiere = '"+message1+"'";
               		Statement statement3s = connection.createStatement();
    				ResultSet result3s = statement3s.executeQuery(sqls); 				
    				while(result3s.next()) {
   					 libeler = result3s.getString("descrip_semaine");
   					nbrsemaine = result3s.getString("semaine");
	            	  System.out.println("nombre libeller : "+libeler);
	            	  System.out.println("nombre libeller : "+nbrsemaine);
    				}
                	 
					session.getBasicRemote().sendText("notification"+"@"+libeler+"&"+nbe+"#"+nbrsemaine+"$");

            		//senDataToClient(session);
            		System.out.println("envoie des donnees terminer avec sucess. ok");
          	  }
	    		
			 }
    		
    		
    		if(message0.equals("senddata")) {
    			message1 = message.substring(message0.length()+1,message.indexOf("&"));
	    		message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));
	    		message3 = message.substring(message2.length()+message1.length()+message0.length()+3,message.indexOf("$"));
	    		senData(session,message1,message2,message3);
    		}
    		
    		
    		if(message0.equals("DashboardFragment")) {
				message1 = message.substring(message0.length()+1,message.indexOf("&"));

    			senDataToClient(session,message1);
    		}
    		
    		if(message0.equals("DashboardFragment3")) {
				message1 = message.substring(message0.length()+1,message.indexOf("&"));

    			senDataToClient(session,message1);
    		}
    		
    		
    		if(message0.equals("sendpermitiontoreadspinner")) {
				 session.getBasicRemote().sendText("teakstosendautorisation"+"@");
    		}
    		
    		if(message.substring(0,4).equals("end@")) {
    			start = false;
    			convertStringToImageByteArray(messageN, message10);
    			System.out.println("ceci est votre fin");
    		}
    		
    		if(start) {
        		messageN += message;
        		System.out.println(messageN);
        	} 
    		
    		
    		switch(message0)
    		{
    		case "begin" :
    			start = true;
    			break;
    		
    		case "insert" :
    		
    																//	session.getBasicRemote().sendText("reception"+"@"+data1+"&"+data1+"#"+data2+"$"+data1+"%"+data2+"¼"+data1+"½"+data2+"~"+data1+"*"+data2+"¥");				          
				 																															
    			
    	    message1 = message.substring(message0.length()+1,message.indexOf("&"));
    		message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));
    		message3 = message.substring(message2.length()+message1.length()+message0.length()+3,message.indexOf("$"));
    		message4 = message.substring(message3.length()+message1.length()+message2.length()+message0.length()+4,message.indexOf("%"));
    		message5 = message.substring(message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+5,message.indexOf("¼"));
    		message6 = message.substring(message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+6,message.indexOf("½"));
    		message7 = message.substring(message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+7,message.indexOf("~"));
    		message8 = message.substring(message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+8,message.indexOf("*"));
    	    message9 = message.substring(message8.length()+message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+9,message.indexOf("¥"));
    	    message10 = message.substring(message9.length()+message8.length()+message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+10,message.length());
    
    	    
    	    																					//    data1+"&"+data2+"#"+data3+"%"+data4+"$"+data5+"%"+data6+"¼"+data7+"½"+data8+"~"+data9+"*"+data10
    	    
    	    
              System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
              System.out.println("onMessage::From=" + session.getId() + " message1=" + message1);
              System.out.println("onMessage::From=" + session.getId() + " message2=" + message2);
              System.out.println("onMessage::From=" + session.getId() + " message3=" + message3);
              System.out.println("onMessage::From=" + session.getId() + " message4=" + message4);
              System.out.println("onMessage::From=" + session.getId() + " Message=" + message5);
              System.out.println("onMessage::From=" + session.getId() + " message1=" + message6);
              System.out.println("onMessage::From=" + session.getId() + " message2=" + message7);
              System.out.println("onMessage::From=" + session.getId() + " message3=" + message8);
              System.out.println("onMessage::From=" + session.getId() + " message4=" + message9);
              System.out.println("onMessage::From=" + session.getId() + " message4=" + message10);
              countId++;
              insertRecord(message1,message2,message3,message4,message5,message6,message7,message8,message9,message10);
              break;
    		
    		
    		case "update" :
    		
        	    message1 = message.substring(message0.length()+1,message.indexOf("&"));
        		message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("~"));
        		message3 = message.substring(message2.length()+message1.length()+message0.length()+3,message.indexOf("#"));
        		message4 = message.substring(message3.length()+message1.length()+message2.length()+message0.length()+4,message.length());
                  System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
                  System.out.println("onMessage::From=" + session.getId() + " message1=" + message1);
                  System.out.println("onMessage::From=" + session.getId() + " message2=" + message2);
                  System.out.println("onMessage::From=" + session.getId() + " message3=" + message3);
                  System.out.println("onMessage::From=" + session.getId() + " message4=" + message4);
        		  
                  countId++;
                  updateRecord(message1,message2,message3,message4);
                  break;
     
    			
    		case "select" :
    	       message1 = message.substring(message0.length()+1, message.length());
    	       
    		   getProductById(message1,session);
    	       break;
    		case "delete" :
    			 message1 = message.substring(message0.length()+1, message.length());
    			 deleteRecord(message1);
    			 break;
    		}
		
    		
    	}
    	  
    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }

    	
        private  final String INSERT_PRODUCT_SQL = "INSERT INTO voiture" +
            "  (model,num_immatriculation,kilometrage,date_achat,type_carburant,nombre_place,pays_immatriculation,prix_location,marque,image) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        public void insertInscription(String message1, String message2, String message3, String message4, String message5, String message6) throws SQLException {
              final String INSERT_PRODUCT_SQL1 = "INSERT INTO client" +
                    "  (nom,prenom,adresse,email,login,password) VALUES " +
                    " (?, ?, ?, ?, ?, ?);";
        	System.out.println(INSERT_PRODUCT_SQL1);
            // Step 1: Establishing a Connection

                // Step 2:Create a statement using connection object
                
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL1);
                
                preparedStatement.setString(1, message1);
                preparedStatement.setString(2, message2);
                preparedStatement.setString(3, message3);
                preparedStatement.setString(4, message4);
                preparedStatement.setString(5, message5);
                preparedStatement.setString(6, message6);
                
                
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            

            }
        
        public void insertRecord(String message1, String message2, String message3, String message4, String message5, String message6, String message7, String message8, String message9, String message10) throws SQLException {
            System.out.println(INSERT_PRODUCT_SQL);
            // Step 1: Establishing a Connection

                // Step 2:Create a statement using connection object
                
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
                
                preparedStatement.setString(1, message1);
                preparedStatement.setString(2, message2);
                preparedStatement.setString(3, message3);
                preparedStatement.setString(4, message4);
                preparedStatement.setString(5, message5);
                preparedStatement.setString(6, message6);
                preparedStatement.setString(7, message7);
                preparedStatement.setString(8, message8);
                preparedStatement.setString(9, message9);
                preparedStatement.setString(10, message10);
                
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            

            }
   
        public void getProductById(String message1, Session session) throws IOException {
            // using try-with-resources to avoid closing resources (boiler plate
            // code)
        	 String SELECT_ALL_QUERY = "select * from produits where nom_produit = '"+message1+"'";
            // Step 1: Establishing a Connection
         
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = null;
				try {
					preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				{
             
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                ResultSet rs = null;
				try {
					rs = preparedStatement.executeQuery();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String nom = null;
				String rayon = null;
				String quantite = null;
				String prix = null;
                // Step 4: Process the ResultSet object.
                try {
					while (rs.next()) {
						 int id = rs.getInt("id_produit");
					     nom = rs.getString("nom_produit");
					     rayon = rs.getString("nom_rayon");
					     quantite = rs.getString("quantite");
					     prix = rs.getString("prix");
					    System.out.println(id + "," + nom + "," + rayon + "," + quantite + "," + prix);
					}
					session.getBasicRemote().sendText(nom+"~"+rayon+"&"+quantite+"@"+prix);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                }
                }
	
	
	public void updateRecord(String message1,String message2,String message3,String message4) throws SQLException {
		String UPDATE_PRODUCT_SQL = "update produits set nom_rayon = ?, quantite = ?, prix = ? where nom_produit = '"+message1+"'";
		
		System.out.println(UPDATE_PRODUCT_SQL);
        // Step 1: Establishing a Connection
        
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL); {
            	preparedStatement.setString(1, message2);
                preparedStatement.setString(2, message3);
                preparedStatement.setString(3, message4);

            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
       
        // Step 4: try-with-resource statement will auto close the connection.
    }

    
 
}
	
	public void deleteRecord(String message1) throws SQLException {
		
		String DELETE_PRODUCT_SQL = "delete from produits where nom_produit = '"+message1+"'";
        System.out.println(DELETE_PRODUCT_SQL);

        // Step 1: Establishing a Connection
        
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL); {
          //  preparedStatement.setString(1, message1);

            // Step 3: Execute the query or update query
            int result = preparedStatement.executeUpdate();
            System.out.println("Number of records affected :: " + result);
        } 
	}

	
    /* chargement du homeActivity */
    public void senData(Session session,String message1,String message2,String message3) {
    	
    	String data1 = "";
    	String data2 = "";
    	String data3 = "";
    	String data4 = "";
    	String data5 = "";
    	String data6 = "";
    	String data7 = "";
    	String data8 = "";
    	String data9 = "";
    	
	String sql1 = "SELECT * FROM raptor.planning_emploie_de_temps WHERE jours = '"+message1+"' and semaine = '"+message2+"' and filiere = '"+message3+"'";
    	
    	try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql1);
			
			while(result.next()) {
		
				data1 = result.getString("descrip_semaine");
				data2 = result.getString("matiere1");
				data3 = result.getString("ens_matiere1");
				data4 = result.getString("matiere2");
				data5 = result.getString("ens_matiere2");
				data6 = result.getString("matiere3");
				data7 = result.getString("ens_matiere3");
				data8 = result.getString("matiere4");
				data9 = result.getString("ens_matiere4");
		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			 getTime(500);
		        System.out.println("data1 : "+data1);
		        System.out.println("data2 : "+data2);
		        System.out.println("data3 : "+data3);
		        System.out.println("data4 : "+data4);
		        System.out.println("data5 : "+data5);
		        System.out.println("data6 : "+data6);
		        System.out.println("data7 : "+data7);
		        System.out.println("data8 : "+data8);
		        System.out.println("data9 : "+data9);
		        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();        
				String result = "Date is 11/07/2021 19:19:30";
				String dateToStr = dateFormat.format(date);
				System.out.println("Date is "+ dateToStr);
					session.getBasicRemote().sendText("receptiondatareussie"+"@"+data1+"&"+data2+"#"+data3+"$"+data4+"%"+data5+"¼"+data6+"½"+data7+"~"+data8+"*"+data9+"¥"+dateToStr+"");				          
			
			 
			 } catch (IOException e) {
               e.printStackTrace();
           }
    	
        System.out.println("fin de la recuperation des donne de la bd...");
    }
	
    /* chargement du homeActivity */
    public void senDataToClient(Session session,String message) {
    	
    	String option = "";
        System.out.println("matricule : "+message);
        
        option = getFiliere(message);
    	
    	DateFormat format1 = new SimpleDateFormat("EEEEE");
        Date date4 = new Date();
        DateFormat.getDateInstance(DateFormat.FULL, Locale.FRENCH);
        String datedj = format1.format(date4);
        datedj.toLowerCase();
        if(datedj.equals("Monday")) {
        	datedj = "lundi";
        }
        
        if(datedj.equals("Tuesday")) {
        	datedj = "mardi";
        }
        
        if(datedj.equals("Wednesday")) {
        	datedj = "mercredi";
        }
        
        if(datedj.equals("Thursday")) {
        	datedj = "jeudi";
        }
        
        if(datedj.equals("Friday")) {
        	datedj = "vendredi";
        }
        
        if(datedj.equals("Saturday")) {
        	datedj = "samedi";
        }
        
        
        
     //  datedj = "mardi";
	       System.out.println("date du jour  : "+datedj );
           System.out.println("filiere : "+option);
           System.out.println("jours : "+datedj);

    	
    	String sql = "SELECT * FROM raptor.planning_emploie_de_temps WHERE filiere = '"+option+"' or jours = '"+datedj+"' ORDER BY id DESC";
    	
    	try {
            System.out.println("initialisation de la recuperation des donne de la bd...");
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			boolean echec_requete_sql = true;
            while(result.next()) {
            	echec_requete_sql = false;
                System.out.println("debut de la recuperation des donne de la bd...");

				String data1 = result.getString("descrip_semaine");
				String data2 = result.getString("matiere1");
				String data3 = result.getString("ens_matiere1");
				String data4 = result.getString("matiere2");
				String data5 = result.getString("ens_matiere2");
				String data6 = result.getString("matiere3");
				String data7 = result.getString("ens_matiere3");
				String data8 = result.getString("matiere4");
				String data9 = result.getString("ens_matiere4");
				String data10 = result.getString("semaine");
				
				SimpleDateFormat sdfr = new SimpleDateFormat("dd-MM-yyyy");

				//String libeler1 = "01/04/2021 au 06/04/2021";
				String libeler1 = data1;
				String libeler = libeler1.replace("/", "-");
				String date1 = libeler.substring(0,libeler.indexOf(" "));
				String date2 = libeler.substring(date1.length()+4,libeler.length());
				  System.out.println("libeler1  : "+libeler1 );
			       System.out.println("libeler   : "+libeler );
			       System.out.println("date1  : "+date1 );
			       System.out.println("date2   : "+date2 );
			       
			       DateFormat format1f = new SimpleDateFormat("dd-MM-yyyy");
			        Date date1f = new Date();
			        String datedjf = format1f.format(date1f);
				       System.out.println("date du jours  : "+datedjf );

			       
				      Date datedujour = sdfr.parse(datedjf);
				      //  Date datedujour = sdfr.parse("04-04-2021");
		        Date dateAvant = sdfr.parse(date1);
			    Date dateApres = sdfr.parse(date2);
			    long diff1 = datedujour.getTime();
			    long diff2 = dateAvant.getTime();
			    long diff3 = dateApres.getTime();
		        int res1 = (int) (diff1 / (1000*60*60*24));
		        int res2 = (int) (diff2 / (1000*60*60*24));
		        int res3 = (int) (diff3 / (1000*60*60*24));
			       System.out.println("datedujour  : "+res1 );
			       System.out.println("dateAvant   : "+res2 );
			       System.out.println("dateApres   : "+res3 );
			       
			       if(res2 < res1 && res1 < res3 ) {
				       System.out.println("la date du jour es compris entre la date d'avant et la date d'apres");
				       try {
							 getTime(500);
							 DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
								Date date = new Date();        
								String dateToStr = dateFormat.format(date);
								System.out.println("Date is "+ dateToStr);
							 
								 session.getBasicRemote().sendText("receptioninit"+"@"+data1+"&"+data2+"#"+data3+"$"+data4+"%"
								 +data5+"¼"+data6+"½"+data7+"~"+data8+"*"+data9+"¥"+data10+"^"+datedj+"?"+dateToStr+"!");	
								return ;
			
							 } catch (IOException e) {
				                e.printStackTrace();
				            }
			       } else {
				       System.out.println("cette date n'es  pas compris entre la date d'avant et la date d'apres");
			       }
		
			}
            
            if(echec_requete_sql) {
                System.out.println("debut de la recuperation des donne de la bd...");

            	 session.getBasicRemote().sendText("echeqreceptioninit"+"@"+datedj+"&");	
            }
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("fin de la recuperation des donne de la bd...");
    }
   
    public void getTime(int time) {
      	 try {
               Thread.sleep(time);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
      }
    
    public static void convertStringToImageByteArray(String imageString, String namefile){
       //int i++;
       byte [] imageInByteArray = null;
       OutputStream outputStream = null;
       try {
       	  imageInByteArray = Base64.decodeBase64(imageString);
       } catch (IllegalArgumentException e) {
           System.out.println("IllegalArgumentException...");
           e.printStackTrace();
       }catch (NullPointerException e) {
           System.out.println("NullPointerException...");
           e.printStackTrace();
       }
       
		 String path = "E:\\DossierImagesUpload\\";

       try {
           outputStream = new FileOutputStream(path+namefile);
           outputStream.write(imageInByteArray);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }finally{
           try {
               outputStream.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       
   }

    public String getFiliere(String matricule) {
    	String option = null;
    	
	String sql1 = "SELECT * FROM raptor.etudiant WHERE matricule = '"+matricule+"'";
    	
    	try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql1);
			
			while(result.next()) {
		
				option = result.getString("option");
		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return option;
    }
	
    public String getSemaine(String matricule) {
    	String nbrsemaine = null;
    	
    	 System.out.println("nombre d'article n'es pas egale...");
    	 System.out.println("envoie des donnees au client en cour...");
    	 
   					
		try {
			String sqls = "SELECT * FROM raptor.planning_emploie_de_temps where filiere = '"+getFiliere(matricule)+"'";
	   		Statement statement3s = connection.createStatement();
			ResultSet result3s = statement3s.executeQuery(sqls); 
			while(result3s.next()) {
				nbrsemaine = result3s.getString("semaine");
			  System.out.println("nombre libeller : "+nbrsemaine);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
		//senDataToClient(session);
		System.out.println("envoie des donnees terminer avec sucess. ok");
    	
    	return nbrsemaine;
    }
    
    public void connectdatabase() {
    	
//    	String jdbcurl = "jdbc:postgresql://localhost:5432/pfedb";
//    	String username = "postgres";
//    	String password = "poldo203";
    	
    	
    	String jdbcurl = "jdbc:postgresql://209.209.40.92:35095/sgetdb";
    	String username = "raptor";
    	String password = "raptor203";
    	
    	try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
            System.out.println("class not found !!!");
			e1.printStackTrace();
		}
    	
    	
    	try {
    		 connection = DriverManager.getConnection(jdbcurl,username,password);
           
            
            if(connection != null) {
                System.out.println("Connected to postgresql server successfully...");

            } else {
                System.out.println("connectin is not done");
            }

    	} catch (SQLException e) {
			// TODO Auto-generated catch block
            System.out.println("error in Connecting to postgresql server");
			e.printStackTrace();
		}
    	
    }
    
	
}
       