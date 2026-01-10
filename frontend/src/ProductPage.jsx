import { useParams } from 'react-router-dom';
import { useState } from 'react';
import './ProductPage.css';

const ProductPage = () => {
  const { productId } = useParams();

  // 🔧 Symulacja danych z backendu
  const product = {
    id: productId,
    name: "Koszulka",
    category: "Odzież",
    price: 99.99,
    image: "https://via.placeholder.com/400",
    isInStock: true,
  };

  const [quantity, setQuantity] = useState(1);

  const handleAddToCart = () => {
    console.log('Dodano do koszyka:', {
      productId: product.id,
      quantity,
    });
  };

  const handleAddToFavorites = () => {
    console.log('Dodano do ulubionych:', product.id);
  };

  return (
    <div className="product-page">
      <div className="product-image">
        <img src={product.image} alt={product.name} />
      </div>

      <div className="product-details">
        <h1>{product.name}</h1>

        <p className="category">Kategoria: {product.category}</p>
        <p className="price">{product.price} zł</p>

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
                value={quantity}
                onChange={(e) => setQuantity(Number(e.target.value))}
              />
            </div>

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
  );
};

export default ProductPage;
