import { useState } from 'react'
import './App.css'
import ProductGrid from './ProductGrid';
import React from 'react';
import { Routes, Route, useParams } from 'react-router-dom';
import Header from './Header';
import ProductPage from './ProductPage';

// --- PROSTE KOMPONENTY (STRONY) ---
// Normalnie trzymałbyś je w osobnych plikach, ale na Hackathon wrzucam tu dla szybkości

// 1. Strona Główna
const HomePage = () => (
  <div style={{ padding: '20px' }}>
    <h1>Strona Główna</h1>
    <p>Tu będą kafelki z polecanymi produktami.</p>
  </div>
);


const CategoryPage = () => {
  const { nazwaKategorii } = useParams(); // To wyciąga końcówkę z adresu URL
  return (
    <div style={{ padding: '20px' }}>
      <h1>Kategoria: {nazwaKategorii}</h1>
      <p>Tu wyświetlimy produkty tylko z tej kategorii.</p>
    </div>
  );
};

// 3. Strona Koszyka
const CartPage = () => (
  <div style={{ padding: '20px' }}>
    <h1>Twój Koszyk</h1>
    <p>Lista zakupów...</p>
  </div>
);

const ShopPage = () => {
  console.log("render shop page");
  return (
      <div>
        <ProductGrid />
      </div>
  )
}

// --- GŁÓWNA APLIKACJA ---
function App() {
  console.log("main app")
  return (
    <div>
      <Header />
      
      {/* Tutaj decydujemy co wyświetlić pod nagłówkiem */}
      <Routes>
        <Route path="/" element={<HomePage />} />
        {/* :nazwaKategorii to zmienna - złapie cokolwiek wpiszesz po ukośniku */}
        <Route path="/kategoria/:nazwaKategorii" element={<CategoryPage />} />
        <Route path="/koszyk" element={<CartPage />} />
        <Route path="/shop" element={<ShopPage />} />
        <Route path="/shop/produkt/:productId" element={<ProductPage />} />
      </Routes>

    </div>

  )
}

export default App;