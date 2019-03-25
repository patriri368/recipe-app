package com.patrycja.recipeapp.bootstrap;

import com.patrycja.recipeapp.domain.*;
import com.patrycja.recipeapp.repositories.CategoryRepository;
import com.patrycja.recipeapp.repositories.RecipeRepository;
import com.patrycja.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    //przy kazdym przeladowaniu kontekstu aplikacji wykonywana jest metoda saveAll zapisujaca do bazy przepisy
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data");
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        //pobiera UOMy
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("sztuka");

        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("łyżka");

        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("łyżeczka");

        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("odrobina");

        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("kufel");

        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("szklanka");

        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        //pobranie obiektu z Optional'a
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teapoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = dashUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        //pobierz Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("Amerykańska");

        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Meksykańska");

        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //przepis na Guacamole
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfekcyjne Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.ŁATWY);
        guacRecipe.setDirections("1.\tPokrój awokado, usuń miąższ: pokrój awokado na pół. Usuń ziarno. Odkrój wnętrze awokado za pomocą tępego noża i zgarnij miąższ łyżką. \n" +
                "2.\tMiazga za pomocą widelca: Za pomocą widelca z grubsza zetrzeć awokado. (Nie przesadzaj! Guacamole powinno być z dużymi kawałkami.) \n" +
                "3.\tDodaj sól, sok z limonki i resztę: Posyp solą i pokrop sokiem z limonki (lub cytryny). Kwas w soku z limonki zapewni równowagę inteksywności awokado i pomoże opóźnić brązowienie awokado. Dodaj posiekaną cebulę, kolendrę, czarny pieprz i chilli. Papryka chilli różni się w zależności od pikantności. Zacznij więc od połowy jednej papryczki chili i dodaj do guacamole pożądany stopień pikantności. Pamiętaj, że wiele z tego robi się, aby posmakować ze względu na zmienność świeżych składników. Zacznij od tego przepisu i dostosuj się do swojego gustu. \n" +
                "4.\tPrzykryj folią i schłodź do przechowywania: Umieść plastikową folię na powierzchni guacamole i zabezpiecz ją przed dostępem powietrza. (Tlen w powietrzu powoduje utlenianie, które zmieni kolor guacamole.) Przechowywać w lodówce, aż będzie gotowy do podania. Chłodzenie pomidorów szkodzi ich smakowi, więc jeśli chcesz dodać pokrojony pomidor do guacamole, dodaj go tuż przed podaniem. Czytaj więcej: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd\n");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("Aby uzyskać bardzo szybko guacamole, weź 1/4 szklanki salsy i wymieszaj ją z tłustym awokado. Nie krępuj się eksperymentować! Jeden klasyczny meksykański guacamole zawiera nasiona granatu i kawałki brzoskwiń (ulubieniec 2próbuj guacamole z dodatkiem ananasa, mango lub truskawek." +
                "\n" +
                "\n" +
                "Czytaj więcej: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");

        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        //dodawanie skladnikow do przepisu
        guacRecipe.addIngredient(new Ingredient("dojrzałe awokado", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("sól koszerna", new BigDecimal(".5"), teapoonUom));
        guacRecipe.addIngredient(new Ingredient("świeży sok z limonki lub sok z cytryny", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("posiekana czerwona cebula lub cienko pokrojona w plasterki zielona cebula", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("papryczki chilli serrano, łodygi i nasiona usunięte, posiekane", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("świeżo starty czarny pieprz", new BigDecimal(2), dashUom));
        guacRecipe.addIngredient(new Ingredient("dojrzały pomidor, nasiona i miazga usunięta, posiekany", new BigDecimal(".5"), eachUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setServings(4);
        guacRecipe.setSource("Simply Recipes");

        //dodawanie do zwracanej listy przepisow
        recipes.add(guacRecipe);

        //przepis na Taco
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Pikantne taco z grillowanym kurczakiem");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.UMIARKOWANY);

        tacosRecipe.setDirections("1.\tPrzygotuj grill gazowy lub węglowy dla średnio-wysokiego, bezpośredniego ogrzewania. \n" +
                "2.\tPrzygotuj marynatę i obtocz kurczaka: w dużej misce wymieszaj proszek chili, oregano, kminek, cukier, sól, czosnek i skórkę pomarańczową. Wymieszaj sok pomarańczowy i oliwę z oliwek, aby uzyskać luźną pastę. Dodaj kurczaka do miski i wrzuć na wierzch. Odłóż na bok, aby marynować, gdy grill się podgrzeje, a ty przygotujesz resztę dodatków.\n" +
                "3.\tGrilluj kurczaka: Grilluj kurczaka przez 3 do 4 minut z każdej strony lub dopóki termometr nie zostanie włożony do najgrubszej części rejestrów mięsnych 165F. Przenieś na talerz i pozostaw do odpoczęcia na 5 minut. \n" +
                "4.\tRozgrzej tortille: Umieść każdą tortillę na grillu lub na gorącej, suchej patelni na średnim ogniu. Jak tylko zobaczysz kieszenie powietrza, zaczną się wydymać w tortilli, obróć je szczypcami i podgrzej przez kilka sekund po drugiej stronie. Owiń rozgrzane tortille w ręczniku, aby je rozgrzać do momentu podania. \n" +
                "5.\tZłożenie tacos: pokroić kurczaka w paski. Na każdej tortilli umieść niewielką garść rukoli. Na wierzchu plasterki kurczaka, plasterki awokado, rzodkiewki, pomidory i plasterki cebuli. Skrop rozrzedzoną kwaśną śmietaną. Podawać z klinami z limonki. \n" +
                "Czytaj więcej: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("Mamy rodzinne motto i to jest: Wszystko idzie lepiej z tortillą. Każdy rodzaj pozostałości może wejść do ciepłej tortilli, zwykle ze zdrową dawką marynowanych jalapenos. Zawsze mogę powąchać przekąskę późną nocą, gdy zapach kafli ogrzewających się w gorącej patelni na piecu przepływa przez dom. Dzisiejsze tacos są bardziej celowe - celowy posiłek zamiast skrytej przekąski o północy! Najpierw krótko marynuję kurczaka w pikantnej paście ancho chile w proszku, oregano, kminek i słodki sok pomarańczowy, gdy grill się podgrzewa. Możesz również wykorzystać ten czas na przygotowanie dodatków taco. Grilluj kurczaka, a następnie pozwól mu odpocząć, gdy ogrzewasz tortille. Teraz jesteś gotowy do złożenia tacos i nadziewania. Cały posiłek przygotowuje się w około 30 minut! \n" +
                "\n" +
                "\n" +
                "Czytaj więcej: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");

        tacoNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("suszone oregano", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredient(new Ingredient("suszony kminek", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredient(new Ingredient("cukier", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredient(new Ingredient("sól", new BigDecimal(".5"), teapoonUom));
        tacosRecipe.addIngredient(new Ingredient("ząbek czosnku, posiekany", new BigDecimal(1), eachUom));
        tacosRecipe.addIngredient(new Ingredient("drobno starta skórka pomarańczy", new BigDecimal(1), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("świeżo wyciśnięty sok z pomarańczy", new BigDecimal(3), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("oliwa z oliwek", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("udka z kurczaka bez kości", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("mała tortilla kukurydziana", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredient(new Ingredient("pakowana rukola", new BigDecimal(3), cupsUom));
        tacosRecipe.addIngredient(new Ingredient("średnie dojrzałe awokado, pokrojone w plastry", new BigDecimal(2), eachUom));
        tacosRecipe.addIngredient(new Ingredient("rzodkiewki, cienkie plasterki", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("pomidory cherry, pokrojone na połówki", new BigDecimal("2.5"), eachUom));
        tacosRecipe.addIngredient(new Ingredient("czerwona cebula, cienkie plasterki", new BigDecimal(".25"), eachUom));
        tacosRecipe.addIngredient(new Ingredient("z grubsza posiekana kolendra", new BigDecimal(4), dashUom));
        tacosRecipe.addIngredient(new Ingredient("kwaśna śmietana rozcieńczona 1/4 szklanką mleka", new BigDecimal(4), cupsUom));
        tacosRecipe.addIngredient(new Ingredient("limonka, pokroić w ćwiartki", new BigDecimal(4), eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setServings(4);
        tacosRecipe.setSource("Simply Recipes");

        recipes.add(tacosRecipe);
        return recipes;
    }
}