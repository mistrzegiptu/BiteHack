import "./shop.css";
import { useNavigate } from 'react-router-dom';

function ProductCard({ id, image, title, price }) {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/shop/produkt/${id}`);
  };

  return (
    <div className="product-card" onClick={handleClick} style={{ cursor: 'pointer', border: '1px solid gray', padding: '10px', margin: '10px' }}>
      <img src={image} alt={title} />
      <div className="columnName">
        <h3>{title}</h3>
      </div>
      <div className="columnPrice">
        <h3>{price} zł</h3>
      </div>
    </div>
  );
}

export default ProductCard;
