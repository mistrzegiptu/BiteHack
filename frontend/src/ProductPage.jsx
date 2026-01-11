import { useParams, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import './ProductPage.css';
import productImg from './assets/product.png';

// Odbieramy cartItems
const ProductPage = ({ user, addToCart, addToWishlist, cartItems }) => {
  const { productId } = useParams();
  const navigate = useNavigate();

  // 🔧 Symulacja danych z backendu
  const product = {
    id: productId,
    name: "Koszulka",
    title: "Koszulka",
    category: "Odzież",
    price: 99.99,
    image: productImg,
    isInStock: true,
  };

  const MAX_QUANTITY = 10; // Stała limitu

  const [quantity, setQuantity] = useState(1);
  const [rating, setRating] = useState(4);
  const [showModal, setShowModal] = useState(false);
  const [warningMessage, setWarningMessage] = useState(""); // Nowy stan na komunikaty błędów

  const maxRating = 5;
  const [comments, setComments] = useState([
    {
      id: 1,
      text: "Świetna koszulka, materiał bardzo przyjemny!",
      date: "2026-01-10",
    },
    {
      id: 2,
      text: "Kolor trochę inny niż na zdjęciu, ale ogólnie OK.",
      date: "2026-01-09",
    },
    {
      id: 3,
      text: "Dostawa szybka, wszystko zgodne z opisem.",
      date: "2026-01-08",
    },
  ]);

  // Czyścimy ostrzeżenie po 3 sekundach (opcjonalnie)
  useEffect(() => {
    if (warningMessage) {
      const timer = setTimeout(() => setWarningMessage(""), 4000);
      return () => clearTimeout(timer);
    }
  }, [warningMessage]);

  // LOGIKA WPISYWANIA ILOŚCI (AUTO-KOREKTA)
  const handleQuantityChange = (e) => {
    let val = parseInt(e.target.value);
    
    if (isNaN(val) || val < 1) {
        val = 1;
        setWarningMessage("");
    } else if (val > MAX_QUANTITY) {
        val = MAX_QUANTITY; // Autokorekta do 10
        setWarningMessage(`Maksymalna ilość tego produktu to ${MAX_QUANTITY} sztuk!`);
    } else {
        setWarningMessage(""); // Jest OK
    }
    
    setQuantity(val);
  };

  const handleAddToCart = () => {
    // 1. Sprawdź ile tego produktu już jest w koszyku
    // (cartItems może być undefined jeśli nie został przekazany, więc dajemy fallback [])
    const currentCart = cartItems || [];
    const existingItem = currentCart.find(item => Number(item.id) === Number(product.id));
    const currentQtyInCart = existingItem ? existingItem.quantity : 0;

    // 2. Sprawdź limit
    if (currentQtyInCart + quantity > MAX_QUANTITY) {
        setWarningMessage(`Masz już ${currentQtyInCart} w koszyku. Limit to ${MAX_QUANTITY}.`);
        return; // Blokujemy dodanie
    }

    // 3. Dodaj jeśli ok
    addToCart(product, quantity);
    setWarningMessage(""); // Czyścimy ewentualne błędy
    setShowModal(true);
  };

  const handleAddToFavorites = () => {
    if (!user) {
        navigate('/logowanie');
        return;
    }
    addToWishlist(product);
    console.log("Dodano do ulubionych");
  };

  const handleContinueShopping = () => {
    setShowModal(false);
  };

  const handleGoToCart = () => {
    setShowModal(false);
    navigate('/koszyk');
  };

  return (
    <div className="product-page">
      
      {/* --- MODAL --- */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h3>Produkt dodany do koszyka!</h3>
            <p style={{marginBottom: '25px'}}>Co chcesz zrobić dalej?</p>
            <div className="modal-buttons">
              <button className="btn-continue" onClick={handleContinueShopping}>
                Kontynuuj zakupy
              </button>
              <button className="btn-cart" onClick={handleGoToCart}>
                Przejdź do koszyka
              </button>
            </div>
          </div>
        </div>
      )}

      <div className='product-section'>
        <div className="product-image">
          <img src={product.image} alt={product.name} />
        </div>

        <div className="product-details">
          <h1>{product.name}</h1>

          <p className="category">Kategoria: {product.category}</p>
          <p className="price">{product.price} zł</p>
          <div className="star-rating">
            {Array.from({ length: maxRating }, (_, i) => (
              <span
                key={i}
                className={`star ${i < rating ? 'filled' : ''}`}
              >
                ★
              </span>
            ))}
          </div>

          <p className={product.isInStock ? 'in-stock' : 'out-of-stock'}>
            {product.isInStock ? 'Dostępny' : 'Brak w magazynie'}
          </p>

          {product.isInStock && (
            <>
              <div className="quantity">
                <label>Ilość:</label>
                <input
                  type="number"
                  min="1"
                  max={MAX_QUANTITY} // HTMLowy limit też dodajemy
                  value={quantity}
                  onChange={handleQuantityChange} // Nasza funkcja z autokorektą
                />
              </div>

              {/* CZERWONY KOMUNIKAT BŁĘDU */}
              {warningMessage && (
                  <p style={{ color: 'red', fontWeight: 'bold', marginTop: '-10px', marginBottom: '15px' }}>
                      {warningMessage}
                  </p>
              )}

              <div className="buttons">
                <button className="add-to-cart" onClick={handleAddToCart}>
                  Dodaj do koszyka
                </button>

                <button className="add-to-favorites" onClick={handleAddToFavorites}>
                  Dodaj do ulubionych
                </button>
              </div>
            </>
          )}
        </div>
      </div>
      
      <div className="comments-section">
        <h2>Komentarze</h2>
        <div className="comments-list">
            {comments.length === 0 && <p>Brak komentarzy.</p>}

            {comments.map((comment) => (
            <div key={comment.id} className="comment">
                <p>{comment.text}</p>
                <small>{comment.date}</small>
            </div>
            ))}
        </div>
      </div>
    </div>
  );
};

export default ProductPage;