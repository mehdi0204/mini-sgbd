import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
	
		MiniSGBD db = new MiniSGBD("etudiants.db");

		System.out.println("--- Insertion des étudiants ---");
		for (int i = 0; i < 105; i++) {
			db.insertRecord("Étudiant " + (i + 1));
		}

		System.out.println("\n--- Lecture ciblée ---");
		System.out.println("Enregistrement ID 41 : " + db.readRecord(41));
		System.out.println("\n--- Contenu de la Page 0 (40 étudiants) ---");
		System.out.println("Page 0 (taille " + db.getPage(0).size() + ") : " + db.getPage(0));
		System.out.println("\n--- Contenu de la Page 2 (25 étudiants) ---");
		System.out.println("Page 2 (taille " + db.getPage(2).size() + ") : " + db.getPage(2));
	}
}
