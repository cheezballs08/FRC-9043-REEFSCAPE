# Valkyrie Kod Yazım Standartları

*İnglizce kullanımında gramer hatalarına tolerans gösterilecktir*

## 1. İsimlendirme

### 1.1 Klasörler

- Başka bir yazılım tarafından belirtilmediği sürece klasör isimleri:
  - Hepsi küçük harf
  - Kısaltmasız
  - Açıklayıcı
  - Ingilizce  

  yazılmalıdır.

### 1.2 Dosyalar

- Dosya isimleri java için:
  - PascalCase
  - Kısaltmasız
  - Açıklayıcı
  - Ingilizce

  yazılmalıdır.

  Diğer diller için vakti gelince buraya ekleme yapılacaktır

### 1.3 Veri Tipleri

- Her sınıf, enum ve interface'in isimleri:
  - PascalCase
  - Kısaltmasız
  - Açıklayıcı
  - Ingilizce

  yazılmalıdır. Enum üyeleri de yukarıdaki kurallara göre yazılmalıdır.

### 1.4 Değişkenler

- Değişkenlerin isimleri:
  - camelCase
  - Kısaltmasız
  - Açıklayıcı
  - Ingilizce

  yazılmalıdır.

### 1.5 Sabitler

*Bu kısmın değişmesi kuvvetle muhtemel, camelCase yerine UPPER_SNAKE_CASE kullanımı daha uygun olur gibi geldi.*

- Sabitlerin isimleri:
  - camelCase
  - Kısaltmasız
  - Açıklayıcı
  - Ingilizce

  yazılmalıdır.

### 1.6 Metodlar

- Metodların isimleri:
  - camelCase
  - Kısaltmasız
  - Açıklayıcı
  - Ingilizce

  yazılmalıdır. Metodun parametreleri de yukarıdaki kurallara göre yazılmalıdır. Eğer metod sınıfın constructorü ise metod PascalCase ve sınıf ismiyle aynı yazılmalıdır.

### 1.7 Github Branchleri

- Branchlerin isimleri:
  - kebab-case
  - Açıklayıcı
  - Kısa (Maksimum 15 harf civarı olacak şekilde özen gösteriniz)
  - Ingilizce

  yazılmalıdır. Kısaltmalara büyük anlam kayıpları olmadıkça izin var.

## 2. Kod Açıklamaları

- Kod açıklamaları:
  - Açıklayıcı
  - Türkçe

  yazılmalıdır.

- Ayrıca her metodun bir Javadoc açıklaması bulunmalıdır. Gerekli olduğu zaman da değişken, sabit ve metodların tipini belirten kod açıklamaları da bulunmalıdır.

- Yapılması gerekenler ve kafa karışıklığına sebep olan durumlar TODO'lar ile belirtilmelidir. Örneğin:

```java
  // TODO: Bu metodun parametrelerinin birimi ne olmalı?
  void setSpeed(double xSpeed, double ySpeed, double rSpeed) {
    /* Metod işlemleri... */
  }
```

## 3. Kod Formatı

### 1.1 Tab

- Tab butonu tıklandığında tab yerine iki boş alan üretmelidir. Normalde boş alan üretir ama tab büyüklüğü 4 tür. Bunu VSCode ayarlarından veya sağ alttan değiştirebilirsiniz.

### 1.2 Kod Açıklamaları

- Açıklanan sınıf, değişken, sabit veya metodun hemen üstüne *bitişik* yazılmalıdır

### 1.3 Boşluklar

- Operatörlerden önce ve sonra bir satırlık boşluk bırakılmalıdır

```java
  double xSpeed = 0.0;

  xSpeed = xSpeed * 3;
```

- Kod açıklamalarının "//" belirteçinden sonra boşluk bırakılmalı, aynısı diğer açıklama tipleri için de kullanılmalıdır.

```java
  // Bu hızı belirtir.
  double xSpeed = 0.0;

  // Doğru
```

```java
  //Bu hızı belirtir.
  double xSpeed = 0.0;

  // Yanlış
```

```java
  /* Bu hızı belirtir. */
  double xSpeed = 0.0;

  // Doğru
```

```java
  /*Bu hızı belirtir.*/
  double xSpeed = 0.0;

  // Yanlış
```

- Kodda tipleri ve etki ettikleri şey aynı olan değişkenler ve sabtiler boşluk ile ayrılmamalıdır, Örnek olarak:

```java
  double xSpeed = 0.0;
  double ySpeed = 0.0;
  double rSpeed = 0.0;

  ChassisSpeeds speeds = new ChasissSpeeds(xSpeed, ySpeed, rSpeed);

  double elevatorHeight = 10;

  double elevatorSpeed = 0.0;

  int motorID = 0;
```

- Kodda sınıflardan sonra bir satır boşluk bırakılmalıdır ve sınıf, arayüz, enum, if, for, ve benzeri durumlarda süslü parantez kullanırken boşluklar kullanılmalıdır. Örnek olarak:

```java
public class Main {

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Argüman sayısı 2 olmalıdır.");
    } else {
      System.out.println(args[0] + args[1]);
    }

    return 0;
  }
}
```

- Eğer parametre sayısı çok olan bir metod ile karşılaşılırsa yeni satırlar ile metod bölünmelidir, örneğin:

```java
  public TeleopDriveCommand(
    DrivetrainSubsystem drivetrainSubsystem,
    DriveType driveType,
    Supplier<Double> xSpeedSupplier,
    Supplier<Double> ySpeedSupplier,
    Supplier<Double> rSpeedSupplier  
  ) {
    /* Fonksiyon ile ilgili işlemler...*/
  }
```

### 1.4 Misc

- Array ve array türevi veri tiplerinde klasik for döngüsü yerine for-each döngüsü kullanılmalıdır. Örnek olarak:

```java
  for (int i = 0; i < array.length; i++) {
    System.out.println(array[i]); // Yanlış
  }
```

```java
  for (int value : array) {
    System.out.println(value); // Doğru
  }
```

- Tek satır if yerine ternary operatör kullanılmalıdır. Örnek olarak:

```java
  if (isInverted) x *= -1;
  // Yanlış  
```

```java
  if (x > 0) System.out.println("X pozitif"); else System.out.println("X negatif");
  // Yanlış
```

```java
  x = isInverted ? x * -1 : x;
  // Doğru
```

```java
  System.out.println(x > 0 ? "X pozitif" : "X negatif");
  // Doğru
```
