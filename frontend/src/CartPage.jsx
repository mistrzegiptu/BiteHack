import React, { useState, useEffect } from 'react';
import './Cart.css';
import { FiTrash2 } from 'react-icons/fi';
import { Link } from 'react-router-dom';

// Odbieramy clearCart z propsów
const CartPage = ({ cartItems, removeFromCart, updateQuantity, discountRate, setDiscountRate, clearCart }) => {
  
  const [promoCode, setPromoCode] = useState("");     
  const [promoMessage, setPromoMessage] = useState(""); 

  useEffect(() => {
    if (discountRate > 0) {
      setPromoMessage("Kod aktywny! Twoje zniżki są zapisane.");
    }
  }, [discountRate]);

  // Obliczenia
  const subtotal = cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);
  const discountAmount = subtotal * discountRate; 
  const totalCost = subtotal - discountAmount;

  // Funkcja sprawdzająca kod
  const handleApplyPromo = () => {
    if (promoCode === "BITEHACK2026") {
      setDiscountRate(0.30); 
      setPromoMessage("Kod przyjęty! Rabat 30% naliczony.");
    } else {
      setDiscountRate(0); 
      setPromoMessage("Nieprawidłowy kod rabatowy.");
    }
  };

  // NOWE: Obsługa czyszczenia koszyka z potwierdzeniem
  const handleClearCart = () => {
      if (window.confirm("Czy na pewno chcesz usunąć wszystkie produkty z koszyka?")) {
          clearCart();
      }
  };

  if (cartItems.length === 0) {
    return (
      <div className="empty-cart">
        <h2>Twój koszyk jest pusty</h2>
        <Link to="/shop" className="continue-btn">Wróć do sklepu</Link>
      </div>
    );
  }

  return (
    <div className="cart-container">
      
      {/* NAGŁÓWEK KOSZYKA Z PRZYCISKIEM USUWANIA */}
      <div className="cart-header">
        <h1 className="cart-title">TWÓJ KOSZYK ({cartItems.reduce((a, c) => a + c.quantity, 0)})</h1>
        
        {/* Przycisk czyszczenia */}
        <button className="clear-cart-btn" onClick={handleClearCart}>
            <FiTrash2 style={{ marginRight: '5px', verticalAlign: 'middle' }} />
            Wyczyść koszyk
        </button>
      </div>
      
      <div className="cart-layout">
        
        {/* LEWA STRONA */}
        <div className="cart-items-section">
          {cartItems.map((item) => (
            <div key={item.id} className="cart-item">
              <div className="item-image">
                <img src={item.image} alt={item.title} />
              </div>
              <div className="item-details">
                <h3>{item.title}</h3>
                <p className="item-price-single">{item.price} PLN</p>
                
                <div className="item-controls">
                  <div className="quantity-selector">
                    <label>Ilość:</label>
                    <select 
                      value={item.quantity} 
                      onChange={(e) => updateQuantity(item.id, parseInt(e.target.value))}
                    >
                      {[1,2,3,4,5,6,7,8,9,10].map(n => (
                          <option key={n} value={n}>{n}</option>
                      ))}
                    </select>
                  </div>
                  <button className="remove-btn" onClick={() => removeFromCart(item.id)}>
                    <FiTrash2 />
                  </button>
                </div>
              </div>
              <div className="item-total-price">
                {(item.price * item.quantity).toFixed(2)} PLN
              </div>
            </div>
          ))}
        </div>

        {/* PRAWA STRONA */}
        <div className="cart-summary-section">
          <div className="summary-box">
            <h2>PODSUMOWANIE</h2>
            
            <div className="summary-row">
              <span>Wartość produktów:</span>
              <span>{subtotal.toFixed(2)} PLN</span>
            </div>

            {discountRate > 0 && (
              <div className="summary-row" style={{ color: 'green', fontWeight: 'bold' }}>
                <span>Rabat ({discountRate * 100}%):</span>
                <span>-{discountAmount.toFixed(2)} PLN</span>
              </div>
            )}
            
            <div className="summary-row total">
              <span>SUMA DO ZAPŁATY</span>
              <span>{totalCost.toFixed(2)} PLN</span>
            </div>

            <button className="checkout-btn">PRZEJDŹ DO KASY</button>

            {/* SEKCJ Z KODEM RABATOWYM */}
            <div className="discount-section">
              <p>Masz kod rabatowy?</p>
              <div className="discount-input-group">
                <input 
                  type="text" 
                  placeholder="Wpisz kod..." 
                  value={promoCode}
                  onChange={(e) => setPromoCode(e.target.value)}
                />
                <button onClick={handleApplyPromo}>OK</button>
              </div>
              {/* Komunikat */}
              {promoMessage && (
                <p className={`promo-message ${discountRate > 0 ? 'success' : 'error'}`}>
                  {promoMessage}
                </p>
              )}
            </div>

          </div>
        </div>

      </div>
    </div>
  );
};

export default CartPage;