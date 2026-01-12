# LiveStore 🛍️

LiveStore is a modern Android application built using **Jetpack Compose** and **MVVM**, consuming data from the FakeStore public API.

---

## 🚀 Features
- Splash Screen (System + Custom fallback)
- Product Listing (Electronics & Clothing)
- Beautiful Tab UI
- Product Details Screen
- Shimmer Loading Effect
- Network Error Overlay with Retry
- MVVM + Clean Architecture
- Dependency Injection using Koin
- RxJava (Single.zip for parallel API calls)
- Offline handling (No Internet detection)

---

## 🧠 Architecture
- **Presentation**: Jetpack Compose
- **Domain**: UseCases
- **Data**: Repository + Retrofit
- **State Management**: StateFlow + NetworkState
- **DI**: Koin

---

## 🔌 API Used
- [FakeStore API](https://fakestoreapi.com/)
  - Electronics products
  - Clothing products
  - Product detail

---

## 🧪 Unit Testing
- ViewModel unit tests
- UseCase tests
- Network error handling tests

(See `UNIT_TESTS.md`)

---

## 🧩 Challenges Faced
- Handling retry logic after network failure
- Managing shimmer + overlay together
- Avoiding duplicate API calls during recomposition

---

## 📱 APK
Download and install:
👉 `Application/LiveStore.apk`

---

## 🎥 Code Walkthrough & Testing Video
🔗 Google Drive link (shared access)

---

## 🧑‍💻 Author
**Abhishek Dhawan**
