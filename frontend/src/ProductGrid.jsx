import ProductCard from "./ProductCard.jsx";
import "./shop.css";
import productImg from "./assets/product.png";

const products = [
  { id: 1, title: "Koszulka", price: 99, image: productImg },
  { id: 2, title: "Bluza", price: 199, image: productImg },
  { id: 3, title: "Buty", price: 349, image: productImg },
  { id: 4, title: "Koszulka", price: 99, image: productImg },
  { id: 5, title: "Bluza", price: 199, image: productImg },
  { id: 6, title: "Buty", price: 349, image: productImg },
  { id: 7, title: "Koszulka", price: 99, image: productImg },
  { id: 8, title: "Bluza", price: 199, image: productImg },
  { id: 9, title: "Buty", price: 349, image: productImg },
];


function ProductGrid() {
  console.log("Renderuje shop")
  return (
    <div className="product-grid">
      {products.map(product => (
        <ProductCard
          key={product.id}
          id={product.id}
          image={product.image}
          title={product.title}
          price={product.price}
        />
      ))}
    </div>
  );
}

export default ProductGrid;
