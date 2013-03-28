/**
 * 
 */
package org.esupportail.cookingapp.web.controllers;

import static fj.Ord.ord;
import static fj.Ord.stringOrd;
import static fj.data.Array.array;
import static fj.data.List.iterableList;
import static fj.data.Option.fromNull;
import static fj.data.Option.iif;
import static org.springframework.util.StringUtils.hasText;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.esupportail.commons.beans.AbstractJsfMessagesAwareBean;
import org.esupportail.cookingapp.domain.DomainService;
import org.esupportail.cookingapp.domain.beans.Ingredient;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fj.F;
import fj.Ordering;
import fj.data.Option;

/**
 * @author llevague
 *
 */
@ViewScoped
@ManagedBean
public class IngredientController extends AbstractJsfMessagesAwareBean {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -9078806018655968253L;

	/**
	 * The service.
	 */
	@Inject
	private DomainService domainService;
	
	/**
	 * The {@link Ingredient} list.
	 */
	private List<Ingredient> ingredients;
		
	/**
	 * The selected {@link Ingredient} list.
	 */
	private Ingredient[] selectedIngredients;

	/**
	 * A new {@link Ingredient} to add.
	 */
	private Ingredient newIngredient;
	
	/**
	 * A filtered value.
	 */
	private String filter;
	
	/**
	 * Disable state of the add form.
	 */
	private boolean addFormDisabled = false;
	
	@PostConstruct
	public void init() {
		ingredients = domainService.getIngredients();
		selectedIngredients = new Ingredient[0];
		newIngredient = new Ingredient();
		filter = new String();
	}
	
	public synchronized void pushIngredients() {
		PushContext pc = PushContextFactory.getDefault().getPushContext();
		pc.push("/ingredients", ingredients);
	}
	
	/**
	 * Get the ingredients from the database.
	 * @return
	 */
	public List<Ingredient> getIngredients() {
		final F<String, Option<String>> isNotEmpty = new F<String, Option<String>>() {
			@Override
			public Option<String> f(final String term) {
				return iif(hasText(term), term);
			}
		};
		
		final F<Ingredient, F<Ingredient, Ordering>> ordering = 
				new F<Ingredient, F<Ingredient, Ordering>>() {
			public F<Ingredient, Ordering> f(final Ingredient i1) {
				return new F<Ingredient, Ordering>() {
					public Ordering f(final Ingredient i2) {
						return stringOrd.compare(i1.getName(), i2.getName());
					}
				};
			};
		};

		final Option<String> letter = isNotEmpty.f(filter);
		
		final F<Ingredient, Boolean> filtering = new F<Ingredient, Boolean>() {
			@Override
			public Boolean f(final Ingredient i) {
				return letter.isSome() ? i.getName()
						.startsWith(letter.some()) : Boolean.TRUE;
			}
		};
		return new ArrayList<Ingredient>(iterableList(ingredients)
				.filter(filtering).sort(ord(ordering)).toCollection());
	}
	/**
	 * @return the addable
	 */
	public boolean checkIfAddable() {
		final boolean addable = hasText(newIngredient.getName())
			&& fromNull(domainService.getIngredient(newIngredient.getName())).isNone();
		addFormDisabled = !addable;
		if (addFormDisabled && hasText(newIngredient.getName())) {
			addErrorMessage(null, "ERROR.INGREDIENT.ADD");
		}
		return addable;
	}
	
	/**
	 * Add an {@link Ingredient}.
	 */
	public void addIngredient() {
		if (checkIfAddable()) {
			domainService.addIngredient(newIngredient);
			init();
			pushIngredients();
			addInfoMessage(null, "INFO.INGREDIENT.ADD", newIngredient.getName());
		} else {
			addErrorMessage(null, "ERROR.INGREDIENT.ADD", newIngredient.getName());
		}
	}

	
	/**
	 * Delete selected {@link Ingredient}.
	 */
	public void deleteIngredients() {
		if (array(selectedIngredients).isNotEmpty()) {
			domainService.deleteIngredients(selectedIngredients);
			addInfoMessage(null, "INFO.INGREDIENT.DELETE");
			init();
			pushIngredients();
		}
	}

	/**
	 * @return the newIngredient
	 */
	public Ingredient getNewIngredient() {
		return newIngredient;
	}

	/**
	 * @param newIngredient the newIngredient to set
	 */
	public void setNewIngredient(final Ingredient newIngredient) {
		this.newIngredient = newIngredient;
	}

	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @return the selectedIngredients
	 */
	public Ingredient[] getSelectedIngredients() {
		return selectedIngredients;
	}

	/**
	 * @param selectedIngredients the selectedIngredients to set
	 */
	public void setSelectedIngredients(final Ingredient[] selectedIngredients) {
		this.selectedIngredients = selectedIngredients;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the addFormDisabled
	 */
	public boolean isAddFormDisabled() {
		return addFormDisabled;
	}

	/**
	 * @param addFormDisabled the addFormDisabled to set
	 */
	public void setAddFormDisabled(boolean addFormDisabled) {
		this.addFormDisabled = addFormDisabled;
	}
}