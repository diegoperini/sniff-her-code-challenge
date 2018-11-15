package com.testfairy.sniff_her.mock;

import com.testfairy.sniff_her.entity.AuthenticationToken;
import com.testfairy.sniff_her.entity.Dog;
import com.testfairy.sniff_her.entity.Message;
import com.testfairy.sniff_her.entity.Owner;
import com.testfairy.sniff_her.utility.NumberUtil;
import com.testfairy.sniff_her.utility.ObjectUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ObservableMock {

    // Interface

    @NonNull
    public static <T> Observable<T> mock(@NonNull final Observable<T> obs, Class<?> valueType) {
        ObjectUtil.assertNotNull(obs);

        if (AuthenticationToken.class.equals(valueType)) {
            return (Observable<T>) Observable.just(mockAuthenticationToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread());
        } else if (Dog.class.equals(valueType)) {
            return (Observable<T>) Observable.just(mockDog()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread());
        } else if (Message.class.equals(valueType)) {
            return (Observable<T>) Observable.just(mockMessage()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread());
        } else if (Owner.class.equals(valueType)) {
            return (Observable<T>) Observable.just(mockOwner()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread());
        } else {
            throw new IllegalArgumentException("Argument has no mock implementation.");
        }
    }

    @NonNull
    public static <T> Observable<List<T>> mockList(@NonNull final Observable<List<T>> obs, Class<?> listElementType) {
        ObjectUtil.assertNotNull(obs);

        if (Dog.class.equals(listElementType)) {
            return Observable.just((List<T>)mockDogs()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread());
        } else {
            throw new IllegalArgumentException("Argument has no mock implementation.");
        }
    }

    // Internals

    private static Random random = new Random();

    @NonNull
    private static String randomHumanName() {
        List<String> list = new ArrayList<>();
        list.add("Sparkle Bascom");
        list.add("Dirk Haymond");
        list.add("Kathyrn Holz");
        list.add("Lizeth Cordon");
        list.add("Valentine Basler");
        list.add("Temika Felix");
        list.add("Errol Phung");
        list.add("Tesha Krizan");
        list.add("Agustina Rusek");
        list.add("Kirby Peaslee");
        list.add("Marquerite Parkin");
        list.add("Nina Thibeault");
        list.add("Hai Florian");
        list.add("Kerrie Mass");
        list.add("Kesha Bergman");
        list.add("Leanne Minks");
        list.add("Israel Dacus");
        list.add("Kaci Partridge");
        list.add("Lessie Rolling");
        list.add("Charita Arambula");

        return list.get(random.nextInt(list.size()));
    }

    @NonNull
    private static String randomDogName() {
        List<String> list = new ArrayList<>();
        list.add("Cheeto");
        list.add("Fuzz Aldrin");
        list.add("Nod");
        list.add("Box Keeper");
        list.add("Twerk");
        list.add("Monkey");
        list.add("Gonzo");
        list.add("Excalipurr");
        list.add("Admiral Snuggles");
        list.add("Oatmeal");
        list.add("Whiskey");
        list.add("Mouse");
        list.add("Tumblesnort");
        list.add("Ninny");
        list.add("Shrimp Kin");
        list.add("General Tso");
        list.add("Kitler");
        list.add("Wrangleswatch");
        list.add("Potato");

        return list.get(random.nextInt(list.size()));
    }

    @NonNull
    private static String randomHobby() {
        List<String> list = new ArrayList<>();
        list.add("Acrobatics");
        list.add("Acting");
        list.add("Amateur radio");
        list.add("Animation");
        list.add("Aquascaping");
        list.add("Baking");
        list.add("Baton twirling");
        list.add("Beatboxing");
        list.add("Board/tabletop games");
        list.add("Book restoration");
        list.add("Cabaret");
        list.add("Calligraphy");
        list.add("Candle making");
        list.add("Coffee roasting");
        list.add("Collecting");
        list.add("Coloring");
        list.add("Computer programming");
        list.add("Cooking");
        list.add("Cosplaying");
        list.add("Couponing");
        list.add("Creative writing");
        list.add("Crocheting");
        list.add("Cross-stitch");
        list.add("Crossword puzzles");

        return list.get(random.nextInt(list.size()));
    }

    @NonNull
    private static String randomMovie() {
        List<String> list = new ArrayList<>();
        list.add("Batman v Superman: Dawn of Justice");
        list.add("The Dark Knight Rises");
        list.add("Spectre");
        list.add("Captain America: Civil War");
        list.add("Pirates of the Caribbean: Dead Men Tell No Tales");
        list.add("Maleficent");
        list.add("The Chronicles of Narnia: Prince Caspian");
        list.add("The Lone Ranger");
        list.add("Pirates of the Caribbean: Dead Man's Chest");
        list.add("Man of Steel");
        list.add("The Avengers");
        list.add("Rogue One");
        list.add("The Hobbit: The Desolation of Smaug");
        list.add("Transformers: The Last Knight");
        list.add("Men in Black 3");
        list.add("Oz the Great and Powerfu;");
        list.add("X-Men: The Last Stand");
        list.add("Transformers: Age of Extinction");
        list.add("Battleship");
        list.add("Dawn of the Planet of the Apes");

        return list.get(random.nextInt(list.size()));
    }

    @NonNull
    private static Dog.Gender randomGender() {
        List<Dog.Gender> list = new ArrayList<>();
        list.add(Dog.Gender.MALE);
        list.add(Dog.Gender.FEMALE);

        return list.get(random.nextInt(list.size()));
    }

    @NonNull
    private static String randomDogPicture() {
        List<String> list = new ArrayList<>();
        list.add("https://purr.objects-us-west-1.dream.io/i/JxCX2.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/O376H.png");
        list.add("https://purr.objects-us-west-1.dream.io/i/rrXar.png");
        list.add("https://purr.objects-us-west-1.dream.io/i/2015-09-17-220.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/moo1.png");
        list.add("https://purr.objects-us-west-1.dream.io/i/hAfIa.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/2014-11-0100.22.56.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/AspVJ.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/20170202_102210.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/d6NGw.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/daVkj.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/1219.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/cutekittylaxing.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/20160907_081538-1.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/20170402_222740.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/cute_kitty_by_moonbarkcd16.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/catculator.jpeg");
        list.add("https://purr.objects-us-west-1.dream.io/i/4L2BRJq.jpg");
        list.add("https://purr.objects-us-west-1.dream.io/i/634.jpg");

        return list.get(random.nextInt(list.size()));
    }

    @NonNull
    private static String randomSentence() {
        List<String> list = new ArrayList<>();
        list.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        list.add("In fringilla lectus at ante hendrerit, id maximus erat vehicula.");
        list.add("Maecenas mollis nisi posuere lacus vehicula, ut malesuada lectus ultrices.");
        list.add("Cras finibus ante id tempor porttitor.");
        list.add("Sed id mi vehicula, efficitur lacus non, vehicula massa.");
        list.add("Sed non ante eget erat dictum varius at laoreet risus.");
        list.add("Nam fermentum neque eget est egestas commodo.");
        list.add("In posuere leo a dolor tristique imperdiet.");
        list.add("Donec iaculis dolor at enim tincidunt finibus.");
        list.add("Vestibulum sit amet eros in arcu lacinia finibus dignissim vel ante.");
        list.add("Nulla in ligula malesuada, ultrices felis nec, tempus est.");
        list.add("Ut sit amet ex eleifend, dapibus est sed, volutpat nisi.");
        list.add("Sed imperdiet lacus a ligula aliquam, ac tristique lorem faucibus.");
        list.add("Donec accumsan enim in orci fermentum consequat.");
        list.add("Cras eu augue eget urna ornare varius.");
        list.add("Ut finibus ante vel nisl blandit, ut imperdiet turpis lacinia.");

        return list.get(random.nextInt(list.size()));
    }

    @NonNull
    private static <T> List<T> randomList(@NonNull final Callable<T> callable, int size) {
        ObjectUtil.assertNotNull(callable);
        NumberUtil.assertPositiveInteger(size);

        List<T> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            try {
                list.add(callable.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    @NonNull
    private static AuthenticationToken mockAuthenticationToken() {
        return new AuthenticationToken(randomHobby() + randomDogName());
    }

    @NonNull
    private static Dog mockDog() {
        return new Dog(Math.abs(random.nextInt()), randomGender(), randomDogPicture(), mockOwner());
    }

    @NonNull
    private static List<Dog> mockDogs() {
        return randomList(new Callable<Dog>() {
            @Override
            public Dog call() {
                return mockDog();
            }
        }, 10);
    }

    @NonNull
    private static Message mockMessage() {
        return new Message(randomSentence());
    }

    @NonNull
    private static Owner mockOwner() {
        return new Owner(Math.abs(random.nextInt()), randomHumanName(), randomMovie(), randomList(new Callable<String>() {
            @Override
            public String call() {
                return randomHobby();
            }
        }, Math.abs(random.nextInt(10) + 1)));
    }
}
