import React from 'react';
import { Link } from 'react-router-dom';
import { FiTrash2, FiShoppingBag } from 'react-icons/fi';
import './Wishlist.css';

// Dodajemy addToCart do propsów
const WishlistPage = ({ wishlistItems, removeFromWishlist, addToCart }) => {

  if (wishlistItems.length === 0) {
    return (
      <div className="empty-wishlist">
        <h2>Twoja lista życzeń jest pusta</h2>
        <p>Dodaj produkty, które Ci się podobają, aby mieć je pod ręką.</p>
        <Link to="/shop" className="continue-btn">Przeglądaj sklep</Link>
      </div>
    );
  }

  return (
    <div className="wishlist-container">
      <h1 className="wishlist-title">ULUBIONE ({wishlistItems.length})</h1>
      
      <div className="wishlist-grid">
        {wishlistItems.map((item) => (
          <div key={item.id} className="wishlist-card">
            
            {/* Zdjęcie */}
            <div className="wishlist-image-wrapper">
              <img src={item.image} alt={item.title} />
              
              <button 
                className="remove-wishlist-btn" 
                onClick={() => removeFromWishlist(item.id)}
                title="Usuń z ulubionych"
              >
                <FiTrash2 />
              </button>
            </div>

            {/* Treść */}
            <div className="wishlist-content">
              <h3>{item.title}</h3>
              <p className="wishlist-price">{item.price} PLN</p>
              
              {/* Tutaj dodajemy obsługę kliknięcia */}
              <button 
                className="move-to-cart-btn"
                onClick={() => addToCart(item, 1)} // Dodajemy 1 sztukę
              >
                <FiShoppingBag style={{ marginRight: '8px' }}/> 
                Dodaj do koszyka
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default WishlistPage;