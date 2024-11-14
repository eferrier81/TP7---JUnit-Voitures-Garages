package garages;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.PrintStream;
import java.util.*;

/**
 * Représente une voiture qui peut être stationnée dans des garages.
 */
@RequiredArgsConstructor
@ToString
public class Voiture {

	@Getter
	@NonNull
	private final String immatriculation;
	@ToString.Exclude // On ne veut pas afficher les stationnements dans toString
	private final List<Stationnement> myStationnements = new LinkedList<>();

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws IllegalStateException Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws IllegalStateException {
		if (estDansUnGarage()) {
			throw new IllegalStateException("La voiture est déjà dans un garage.");
		}
		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws IllegalStateException si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws IllegalStateException {
		if (!estDansUnGarage()) {
			throw new IllegalStateException("La voiture n'est pas dans un garage.");
		}
		// Récupère le dernier stationnement et le termine
		myStationnements.get(myStationnements.size() - 1).terminer();
	}

	/**
	 * Vérifie si la voiture est actuellement dans un garage.
	 *
	 * @return true si la voiture est dans un garage, false sinon
	 */
	public boolean estDansUnGarage() {
		return !myStationnements.isEmpty() && myStationnements.get(myStationnements.size() - 1).estEnCours();
	}

	/**
	 * Retourne la liste des garages visités par cette voiture.
	 *
	 * @return une collection des garages visités
	 */
	public Set<Garage> garagesVisites() {
		Set<Garage> garages = new HashSet<>();
		for (Stationnement s : myStationnements) {
			garages.add(s.getGarage());
		}
		return garages;
	}

	/**
	 * Imprime les stationnements de la voiture dans le PrintStream donné.
	 *
	 * @param out le PrintStream où l'on imprime
	 */
	public void imprimeStationnements(PrintStream out) {
		for (Garage garage : garagesVisites()) {
			out.println(garage.toString());
			for (Stationnement s : myStationnements) {
				if (s.getGarage().equals(garage)) {
					out.println(s); // Utilise toString de Stationnement
				}
			}
		}
	}
}
