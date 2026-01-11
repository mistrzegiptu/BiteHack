import './App.css'
import { Routes, Route, useParams } from 'react-router-dom';
import ProductPage from './ProductPage';
import React, { useState, useEffect } from 'react';
import Header from './Header';
import ProductGrid from './ProductGrid';
import CartPage from './CartPage';
import WishlistPage from './WishlistPage';
import LoginPage from './LoginPage';
import ProfilePage from './ProfilePage';
import ContactPage from './ContactPage'; // <--- NOWY IMPORT
import productImg from "./assets/product.png"; 
import GeminiChat from "./GeminiChat.jsx"
import { node } from 'prop-types';

const HomePage = () => (
  <div style={{ padding: '20px', textAlign: 'center' }}>
    <h1>Witamy w LOKALNYM BIZNESIE</h1>
    <p>Sprawdź naszą ofertę w zakładce SKLEP.</p>
  </div>
);

const CategoryPage = () => {
  const { nazwaKategorii } = useParams();
  return (
    <div style={{ padding: '20px' }}>
      <h1>Kategoria: {nazwaKategorii}</h1>
      <ProductGrid categorySlug={nazwaKategorii} />
    </div>
  );
};

const ShopPage = ({searchTerm}) => (
  <div>
    <h2 style={{ textAlign: 'center', marginTop: '20px' }}>Pełna Oferta</h2>
    <ProductGrid searchTerm={searchTerm} />
    <GeminiChat />
  </div>
);

function App() {
  
  const [searchTerm, setSearchTerm] = useState("");

  // 1. DANE (Koszyk, Ulubione, Rabat - bez zmian)
  const [cartItems, setCartItems] = useState(() => {
    const saved = localStorage.getItem("myCart");
    return saved ? JSON.parse(saved) : [{ id: 1, title: "Koszulka Sportowa", price: 99.00, image: productImg, quantity: 1 }];
  });

  const [wishlistItems, setWishlistItems] = useState(() => {
    const saved = localStorage.getItem("myWishlist");
    return saved ? JSON.parse(saved) : [];
  });

  const [discountRate, setDiscountRate] = useState(() => {
    const saved = localStorage.getItem("myDiscount");
    return saved ? JSON.parse(saved) : 0;
  });

  // 2. STAN UŻYTKOWNIKA
  const [user, setUser] = useState(() => {
    const savedUser = sessionStorage.getItem("myUser");
    return savedUser
      ? JSON.parse(savedUser) : null; 
  });

  // --- EFEKTY ---
  useEffect(() => localStorage.setItem("myCart", JSON.stringify(cartItems)), [cartItems]);
  useEffect(() => localStorage.setItem("myDiscount", JSON.stringify(discountRate)), [discountRate]);
  useEffect(() => localStorage.setItem("myWishlist", JSON.stringify(wishlistItems)), [wishlistItems]);
  
  useEffect(() => {
    if (user) {
      sessionStorage.setItem("myUser", JSON.stringify(user));
    } else {
      sessionStorage.removeItem("myUser");
    }
  }, [user]);

  // --- OBLICZENIA POMOCNICZE ---
  const totalItemsInCart = cartItems.reduce((acc, item) => acc + item.quantity, 0);

  // --- FUNKCJE ---
  
  const addToCart = (product, quantityToAdd) => {
    setCartItems(prevItems => {
      const productId = Number(product.id);
      
      const existingItem = prevItems.find(item => Number(item.id) === productId);
      
      if (existingItem) {
        // Zabezpieczenie na poziomie App
        const newQuantity = existingItem.quantity + quantityToAdd;
        if (newQuantity > 10) {
            return prevItems.map(item => 
                Number(item.id) === productId 
                  ? { ...item, quantity: 10 } 
                  : item
              );
        }

        return prevItems.map(item => 
          Number(item.id) === productId 
            ? { ...item, quantity: item.quantity + quantityToAdd } 
            : item
        );
      } else {
        return [...prevItems, { ...product, id: productId, quantity: quantityToAdd }];
      }
    });
  };

  const removeFromCart = (id) => setCartItems(p => p.filter(i => i.id !== id));
  
  const clearCart = () => setCartItems([]);

  const updateQuantity = (id, q) => q >= 1 && setCartItems(p => p.map(i => i.id === id ? { ...i, quantity: q } : i));
  
  const addToWishlist = (product) => {
    setWishlistItems(prevItems => {
      const productId = Number(product.id);
      const exists = prevItems.find(item => Number(item.id) === productId);
      if (exists) return prevItems;
      return [...prevItems, { ...product, id: productId }];
    });
  };

  const removeFromWishlist = (id) => setWishlistItems(p => p.filter(i => i.id !== id));

  const handleLogin = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
    setDiscountRate(0);
  };

  return (
    <div>
      <Header 
        cartCount={totalItemsInCart} 
        wishlistCount={wishlistItems.length}
        user={user} 
        setSearchTerm={setSearchTerm}
      /> 
      
      <Routes>
        <Route path="/" element={<HomePage />} />
        {/* :nazwaKategorii to zmienna - złapie cokolwiek wpiszesz po ukośniku */}
        <Route path="/shop" element={<ShopPage  searchTerm={searchTerm}/>} />
        
        <Route 
          path="/shop/produkt/:productId" 
          element={
            <ProductPage 
              user={user} 
              addToCart={addToCart} 
              addToWishlist={addToWishlist}
              cartItems={cartItems} // <--- WAŻNE: Przekazujemy koszyk, żeby sprawdzać limity
            />
          } 
        />
        <Route path="/kategoria/:nazwaKategorii" element={<CategoryPage />} />
        
        <Route 
          path="/koszyk" 
          element={
            <CartPage 
              cartItems={cartItems} 
              removeFromCart={removeFromCart}
              updateQuantity={updateQuantity}
              discountRate={discountRate}
              setDiscountRate={setDiscountRate}
              clearCart={clearCart}
            />
          } 
        />

        <Route 
          path="/ulubione" 
          element={
            <WishlistPage 
              wishlistItems={wishlistItems} 
              removeFromWishlist={removeFromWishlist}
              addToCart={addToCart}
            />
          } 
        />

        {/* --- NOWA ŚCIEŻKA KONTAKT --- */}
        <Route path="/kontakt" element={<ContactPage />} />

        <Route 
          path="/logowanie" 
          element={<LoginPage handleLogin={handleLogin} />} 
        />
        
        <Route 
          path="/profil" 
          element={<ProfilePage user={user} handleLogout={handleLogout} />} 
        />
      </Routes>
    </div>
  );
}

export default App;