/**
 * M SHAKEEL FABRICS - Web Application Store Logic
 * Complete E-Commerce Storefront, Cart, Checkout, Tracking, and Admin Dashboard
 */

// Initial Catalog Data
const DEFAULT_PRODUCTS = [
  {
    id: 1,
    name: "Royal Boski Silk - 7 Pound Special Edition",
    category: "Men's Wear",
    price: 14500,
    origPrice: 17000,
    image: "https://images.unsplash.com/photo-1598033129183-c4f50c736f10?w=800&auto=format&fit=crop&q=80",
    fabricType: "100% Pure Silk Boski",
    description: "Woven from 100% pure spun Chinese silk filaments, this 7-pound weight Royal Boski delivers incomparable drape, buttery luster, and cooling comfort for formal celebrations and weddings.",
    options: ["4.5 Meters (Full Suit)", "7 Meters (Double Suit)", "Stitched (M)", "Stitched (L)"],
    stock: 14,
    featured: true,
    rating: 4.9,
    reviews: 42
  },
  {
    id: 2,
    name: "Imperial Egyptian Giza Cotton - Platinum Weave",
    category: "Unstitched Fabrics",
    price: 5950,
    origPrice: 6800,
    image: "https://images.unsplash.com/photo-1594938298603-c8148c4dae35?w=800&auto=format&fit=crop&q=80",
    fabricType: "100% Certified Egyptian Cotton",
    description: "Extra-long staple certified Egyptian Giza combed cotton with liquid ammonia finishing. Features silk-like breathability and wrinkle resistance ideal for year-round executive wear.",
    options: ["4.5 Meters (Full Suit)", "7 Meters (Double Suit)"],
    stock: 25,
    featured: true,
    rating: 4.8,
    reviews: 38
  },
  {
    id: 3,
    name: "Heritage Hand-Spun Pure Karandi Winter Suit",
    category: "Men's Wear",
    price: 8400,
    origPrice: 9500,
    image: "https://images.unsplash.com/photo-1617137984095-74e4e5e3613f?w=800&auto=format&fit=crop&q=80",
    fabricType: "Authentic Raw Karandi",
    description: "Artisanal textured pure Karandi loomed in the traditional craft heartlands. Distinctive cross-slub weave that retains warmth while delivering understated regal elegance.",
    options: ["4.5 Meters (Full Suit)", "7 Meters (Heavy Suit)"],
    stock: 19,
    featured: true,
    rating: 4.9,
    reviews: 29
  },
  {
    id: 4,
    name: "Empress Embroidered Chiffon 3-Piece Festive",
    category: "Women's Wear",
    price: 16800,
    origPrice: 19500,
    image: "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=800&auto=format&fit=crop&q=80",
    fabricType: "Pure Crinkle Chiffon & Silk",
    description: "Opulent pure crinkle chiffon shirt intricately adorned with tilla, sequin, and resham floral embroidery. Accompanied by embroidered organza border dupatta and raw silk trousers.",
    options: ["Unstitched (3-Piece)", "Stitched Small", "Stitched Medium", "Stitched Large"],
    stock: 9,
    featured: true,
    rating: 5.0,
    reviews: 51
  },
  {
    id: 5,
    name: "Signature Wrinkle-Free Wash & Wear Kurta Fabric",
    category: "Men's Wear",
    price: 3850,
    origPrice: 4500,
    image: "https://images.unsplash.com/photo-1620799140408-edc6dcb6d633?w=800&auto=format&fit=crop&q=80",
    fabricType: "Micro Poly-Viscose Wash & Wear",
    description: "Specially engineered micro-filament blend with soft poly-viscose finish. Resists creases throughout long days, maintains color brilliance after countless washes.",
    options: ["4.5 Meters (Full Suit)", "Stitched Kurta (M)", "Stitched Kurta (L)"],
    stock: 35,
    featured: false,
    rating: 4.7,
    reviews: 64
  },
  {
    id: 6,
    name: "Noor-e-Zainab Embroidered Silk Velvet Formal Suit",
    category: "Women's Wear",
    price: 21500,
    origPrice: 24000,
    image: "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800&auto=format&fit=crop&q=80",
    fabricType: "Micro 9000 Silk Velvet",
    description: "Micro 9000 pure silk velvet in midnight jewel tone featuring heavy hand-crafted zardozi neckline and kora dabka detailing. Truly regal couture for winter galas.",
    options: ["Unstitched (3-Piece)", "Custom Stitched"],
    stock: 6,
    featured: true,
    rating: 4.9,
    reviews: 23
  },
  {
    id: 7,
    name: "Supreme Crisp White Royal Latha - 100% Combed",
    category: "Unstitched Fabrics",
    price: 4600,
    origPrice: 5200,
    image: "https://images.unsplash.com/photo-1528459801416-a9e53bbf4e17?w=800&auto=format&fit=crop&q=80",
    fabricType: "100% Mercerized Combed Cotton",
    description: "Crisp, traditional white latha fabric mercerized to perfection. Delivers that classic crisp sound and immaculate structure revered for Friday prayers and celebratory wear.",
    options: ["4.5 Meters (Full Suit)", "7 Meters (Heavy Suit)"],
    stock: 28,
    featured: false,
    rating: 4.8,
    reviews: 31
  },
  {
    id: 8,
    name: "Kashmiri Embroidered Pashmina Heritage Shawl",
    category: "New Arrivals",
    price: 18900,
    origPrice: 22000,
    image: "https://images.unsplash.com/photo-1607083206968-13611e3d76db?w=800&auto=format&fit=crop&q=80",
    fabricType: "100% Fine Himalayan Pashmina Wool",
    description: "Artisan woven fine wool pashmina with intricate Sozni needlework across the palu and borders. Feathery soft, ultra-light, yet intensely insulating.",
    options: ["Standard Shawl (2.5m x 1.25m)"],
    stock: 8,
    featured: true,
    rating: 5.0,
    reviews: 19
  }
];

// Persistent state handlers
function getProducts() {
  const stored = localStorage.getItem("msf_products");
  if (!stored) {
    localStorage.setItem("msf_products", JSON.stringify(DEFAULT_PRODUCTS));
    return DEFAULT_PRODUCTS;
  }
  return JSON.parse(stored);
}

function saveProducts(products) {
  localStorage.setItem("msf_products", JSON.stringify(products));
}

function getCart() {
  return JSON.parse(localStorage.getItem("msf_cart") || "[]");
}

function saveCart(cart) {
  localStorage.setItem("msf_cart", JSON.stringify(cart));
  updateCartBadge();
  renderCartDrawer();
}

function getOrders() {
  const stored = localStorage.getItem("msf_orders");
  if (!stored) {
    const samples = [
      {
        orderId: "MSF-78241",
        customerName: "Barrister Daniyal Khan",
        customerPhone: "03001234567",
        customerEmail: "daniyal@example.com",
        deliveryAddress: "House 42, Street 8, Phase 5 DHA",
        city: "Lahore",
        paymentMethod: "Cash on Delivery",
        totalAmount: 14500,
        status: "Shipped",
        itemsSummary: "Royal Boski Silk 7-Pound (4.5 Meters) x 1",
        date: new Date(Date.now() - 86400000 * 2).toLocaleDateString()
      }
    ];
    localStorage.setItem("msf_orders", JSON.stringify(samples));
    return samples;
  }
  return JSON.parse(stored);
}

function saveOrders(orders) {
  localStorage.setItem("msf_orders", JSON.stringify(orders));
}

// App State
let currentCategory = "All";
let currentSort = "featured";
let currentSearch = "";
let discountRate = 0.0;
let appliedPromoCode = "";
let selectedDetailProduct = null;
let selectedDetailOption = "";
let selectedDetailQty = 1;
let isAdminAuthenticated = false;

// Currency Formatter
function formatPKR(amount) {
  return "Rs. " + Number(amount).toLocaleString();
}

// Navigation Router
function navigateTo(viewId) {
  document.querySelectorAll(".page-view").forEach(el => el.classList.remove("active"));
  document.querySelectorAll(".nav-link").forEach(el => el.classList.remove("active"));

  const target = document.getElementById(viewId + "View");
  if (target) {
    target.classList.add("active");
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  // Update navbar active link
  const link = document.querySelector(`.nav-link[href="#${viewId}"]`);
  if (link) link.classList.add("active");

  // View specific initializations
  if (viewId === "home") {
    renderFeaturedProducts();
  } else if (viewId === "shop") {
    renderShopProducts();
  } else if (viewId === "admin") {
    renderAdminPortal();
  }

  // Close mobile nav if open
  document.getElementById("mainNav")?.classList.remove("open");
}

function filterCategoryAndNavigate(category) {
  currentCategory = category;
  navigateTo("shop");
  updateCategoryPills();
  renderShopProducts();
}

// Mobile Menu Toggle
document.getElementById("mobileMenuBtn")?.addEventListener("click", () => {
  document.getElementById("mainNav")?.classList.toggle("open");
});

// Featured Products Render
function renderFeaturedProducts() {
  const container = document.getElementById("featuredProductsContainer");
  if (!container) return;

  const products = getProducts().filter(p => p.featured).slice(0, 4);
  container.innerHTML = products.map(p => createProductCardHtml(p)).join("");
}

// Shop Catalog Render
function renderShopProducts() {
  const container = document.getElementById("shopProductsContainer");
  if (!container) return;

  let products = getProducts();

  // Category filter
  if (currentCategory !== "All") {
    products = products.filter(p => p.category === currentCategory);
  }

  // Search filter
  if (currentSearch.trim()) {
    const q = currentSearch.toLowerCase();
    products = products.filter(p =>
      p.name.toLowerCase().includes(q) ||
      p.fabricType.toLowerCase().includes(q) ||
      p.description.toLowerCase().includes(q)
    );
  }

  // Sorting
  if (currentSort === "price-asc") {
    products.sort((a, b) => a.price - b.price);
  } else if (currentSort === "price-desc") {
    products.sort((a, b) => b.price - a.price);
  } else if (currentSort === "rating") {
    products.sort((a, b) => b.rating - a.rating);
  } else {
    products.sort((a, b) => (b.featured ? 1 : 0) - (a.featured ? 1 : 0));
  }

  if (products.length === 0) {
    container.innerHTML = `
      <div style="grid-column: 1 / -1; text-align: center; padding: 50px 20px;">
        <h3>No Fabrics Found</h3>
        <p style="color: var(--color-text-secondary); margin-top: 6px;">Try adjusting your search query or category filter.</p>
        <button class="btn btn-primary" style="margin-top: 14px;" onclick="resetShopFilters()">Reset Filters</button>
      </div>
    `;
    return;
  }

  container.innerHTML = products.map(p => createProductCardHtml(p)).join("");
}

function resetShopFilters() {
  currentCategory = "All";
  currentSearch = "";
  document.getElementById("catalogSearch").value = "";
  updateCategoryPills();
  renderShopProducts();
}

function updateCategoryPills() {
  document.querySelectorAll(".category-pills .pill").forEach(pill => {
    if (pill.innerText.trim() === currentCategory) {
      pill.classList.add("active");
    } else {
      pill.classList.remove("active");
    }
  });
}

function setCategoryFilter(category) {
  currentCategory = category;
  updateCategoryPills();
  renderShopProducts();
}

function handleSearch(val) {
  currentSearch = val;
  renderShopProducts();
}

function handleSort(val) {
  currentSort = val;
  renderShopProducts();
}

// Product Card HTML generator
function createProductCardHtml(p) {
  let stockBadge = "";
  if (p.stock <= 0) {
    stockBadge = '<span class="stock-pill stock-out">SOLD OUT</span>';
  } else if (p.stock <= 5) {
    stockBadge = `<span class="stock-pill stock-low">ONLY ${p.stock} LEFT</span>`;
  } else if (p.featured) {
    stockBadge = '<span class="stock-pill stock-vip">VIP EXCLUSIVE</span>';
  }

  const origPriceHtml = p.origPrice ? `<span class="orig-price">${formatPKR(p.origPrice)}</span>` : "";

  return `
    <div class="product-card">
      <div class="product-thumb-box" onclick="openProductDetail(${p.id})">
        <img src="${p.image}" alt="${p.name}" loading="lazy">
        ${stockBadge}
        <span class="fabric-type-pill">${p.fabricType.toUpperCase()}</span>
      </div>
      <div class="product-body">
        <div class="product-meta-row">
          <span class="product-category">${p.category}</span>
          <span class="product-rating">★ ${p.rating.toFixed(1)}</span>
        </div>
        <h4 class="product-name" onclick="openProductDetail(${p.id})">${p.name}</h4>
        <div class="product-price-row">
          <div class="price-box">
            <span class="current-price">${formatPKR(p.price)}</span>
            ${origPriceHtml}
          </div>
          <button class="btn-add-mini" title="Add to Bag" ${p.stock <= 0 ? 'disabled style="opacity:0.4;"' : ''} onclick="quickAddToCart(${p.id})">
            +
          </button>
        </div>
      </div>
    </div>
  `;
}

// Open Product Detail
function openProductDetail(productId) {
  const p = getProducts().find(item => item.id === productId);
  if (!p) return;

  selectedDetailProduct = p;
  selectedDetailOption = p.options[0] || "Standard Cut";
  selectedDetailQty = 1;

  const container = document.getElementById("productDetailContainer");
  if (!container) return;

  const origPriceHtml = p.origPrice ? `
    <span class="orig-price" style="font-size: 16px;">${formatPKR(p.origPrice)}</span>
    <span style="background: var(--color-gold-container); color: var(--color-gold-dark); font-weight:700; font-size:11px; padding: 2px 6px; border-radius:4px;">
      ${Math.round(((p.origPrice - p.price) / p.origPrice) * 100)}% OFF
    </span>
  ` : "";

  let stockNotice = "";
  if (p.stock <= 0) {
    stockNotice = `<div class="detail-stock-notice" style="background:#FDE8E8; color:#BA1A1A;">Currently Sold Out. Next weaving batch in production.</div>`;
  } else if (p.stock <= 5) {
    stockNotice = `<div class="detail-stock-notice" style="background:#FEF3C7; color:#D97706;">Low Stock Alert: Only ${p.stock} suits remaining!</div>`;
  } else {
    stockNotice = `<div class="detail-stock-notice" style="background:#DEF7EC; color:#1E7E34;">✓ In Stock: Ready for immediate dispatch (${p.stock} available)</div>`;
  }

  container.innerHTML = `
    <div class="detail-img-box">
      <img src="${p.image}" alt="${p.name}">
    </div>
    <div class="detail-info">
      <span class="section-kicker">${p.category} • ${p.fabricType}</span>
      <h1 class="detail-title">${p.name}</h1>
      <div class="detail-price-row">
        <span class="detail-current-price">${formatPKR(p.price)}</span>
        ${origPriceHtml}
      </div>

      ${stockNotice}

      <div class="options-section">
        <span class="options-label">SELECT YARDAGE / SIZING OPTION:</span>
        <div class="option-selector" id="detailOptionsList">
          ${p.options.map((opt, idx) => `
            <div class="option-btn ${idx === 0 ? 'selected' : ''}" onclick="selectDetailOption(this, '${opt}')">
              <span>${opt}</span>
              <span class="opt-check">✓</span>
            </div>
          `).join("")}
        </div>
      </div>

      <span class="options-label">QUANTITY:</span>
      <div class="qty-control">
        <button class="qty-btn" onclick="adjustDetailQty(-1)">-</button>
        <span class="qty-val" id="detailQtyVal">1</span>
        <button class="qty-btn" onclick="adjustDetailQty(1)">+</button>
      </div>

      <div class="detail-action-row">
        <button class="btn btn-outline" style="flex:1; border-color: var(--color-navy-primary); color: var(--color-navy-primary);"
          ${p.stock <= 0 ? 'disabled' : ''} onclick="addDetailToCart(false)">
          Add to Bag
        </button>
        <button class="btn btn-primary" style="flex:1;"
          ${p.stock <= 0 ? 'disabled' : ''} onclick="addDetailToCart(true)">
          Buy Now (COD)
        </button>
      </div>

      <div class="detail-desc-box">
        <h4>THE WEAVE & FABRIC STORY</h4>
        <p style="color: var(--color-text-secondary); line-height:1.7;">${p.description}</p>
      </div>
    </div>
  `;

  navigateTo("detail");
}

function selectDetailOption(el, optionText) {
  document.querySelectorAll("#detailOptionsList .option-btn").forEach(b => b.classList.remove("selected"));
  el.classList.add("selected");
  selectedDetailOption = optionText;
}

function adjustDetailQty(delta) {
  if (!selectedDetailProduct) return;
  const newQty = selectedDetailQty + delta;
  if (newQty >= 1 && newQty <= selectedDetailProduct.stock) {
    selectedDetailQty = newQty;
    document.getElementById("detailQtyVal").innerText = selectedDetailQty;
  }
}

function addDetailToCart(instantCheckout = false) {
  if (!selectedDetailProduct || selectedDetailProduct.stock <= 0) return;

  const cart = getCart();
  const existingIndex = cart.findIndex(item =>
    item.productId === selectedDetailProduct.id && item.selectedOption === selectedDetailOption
  );

  if (existingIndex > -1) {
    cart[existingIndex].quantity += selectedDetailQty;
  } else {
    cart.push({
      productId: selectedDetailProduct.id,
      name: selectedDetailProduct.name,
      price: selectedDetailProduct.price,
      image: selectedDetailProduct.image,
      selectedOption: selectedDetailOption,
      quantity: selectedDetailQty
    });
  }

  saveCart(cart);

  if (instantCheckout) {
    openCheckoutModal();
  } else {
    toggleCart(true);
  }
}

function quickAddToCart(productId) {
  const p = getProducts().find(item => item.id === productId);
  if (!p || p.stock <= 0) return;

  const cart = getCart();
  const firstOption = p.options[0] || "Standard Cut";
  const existing = cart.find(item => item.productId === p.id && item.selectedOption === firstOption);

  if (existing) {
    existing.quantity += 1;
  } else {
    cart.push({
      productId: p.id,
      name: p.name,
      price: p.price,
      image: p.image,
      selectedOption: firstOption,
      quantity: 1
    });
  }

  saveCart(cart);
  toggleCart(true);
}

// Cart Drawer & Calculations
function toggleCart(forceOpen = null) {
  const drawer = document.getElementById("cartDrawer");
  const backdrop = document.getElementById("cartBackdrop");
  if (!drawer || !backdrop) return;

  const shouldOpen = forceOpen !== null ? forceOpen : !drawer.classList.contains("open");
  if (shouldOpen) {
    renderCartDrawer();
    drawer.classList.add("open");
    backdrop.classList.add("open");
  } else {
    drawer.classList.remove("open");
    backdrop.classList.remove("open");
  }
}

function updateCartBadge() {
  const cart = getCart();
  const totalCount = cart.reduce((sum, item) => sum + item.quantity, 0);
  const badge = document.getElementById("cartCountBadge");
  if (badge) badge.innerText = totalCount;
}

function renderCartDrawer() {
  const cart = getCart();
  const container = document.getElementById("cartItemsList");
  const footer = document.getElementById("cartFooter");
  const thresholdBar = document.getElementById("shippingBarContainer");

  if (!container || !footer) return;

  if (cart.length === 0) {
    container.innerHTML = `
      <div style="text-align:center; padding: 60px 20px; color: var(--color-text-secondary);">
        <p style="font-size: 16px; font-weight:700; color: var(--color-navy-dark);">Your bag is empty</p>
        <p style="font-size: 12px; margin-top: 6px;">Explore our pure silk Boski and Egyptian cottons.</p>
        <button class="btn btn-primary" style="margin-top: 16px;" onclick="toggleCart(); navigateTo('shop');">Explore Shop</button>
      </div>
    `;
    footer.style.display = "none";
    if (thresholdBar) thresholdBar.style.display = "none";
    return;
  }

  footer.style.display = "block";
  if (thresholdBar) thresholdBar.style.display = "block";

  container.innerHTML = cart.map((item, idx) => `
    <div class="cart-item-card">
      <img src="${item.image}" alt="${item.name}" class="cart-item-thumb">
      <div class="cart-item-info">
        <h5 class="cart-item-name">${item.name}</h5>
        <div class="cart-item-opt">${item.selectedOption}</div>
        <div class="cart-item-price">${formatPKR(item.price)}</div>
        <div class="cart-item-bottom">
          <div class="qty-control" style="margin-bottom:0;">
            <button class="qty-btn" style="padding: 2px 8px;" onclick="updateCartItemQty(${idx}, -1)">-</button>
            <span class="qty-val" style="padding: 0 6px; font-size:12px;">${item.quantity}</span>
            <button class="qty-btn" style="padding: 2px 8px;" onclick="updateCartItemQty(${idx}, 1)">+</button>
          </div>
          <button style="background:none; border:none; color:var(--color-error); cursor:pointer; font-size:12px;" onclick="removeCartItem(${idx})">
            Remove
          </button>
        </div>
      </div>
    </div>
  `).join("");

  // Subtotal & Shipping calculation
  const subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  const discount = subtotal * discountRate;
  const discountedSubtotal = subtotal - discount;
  const deliveryFee = discountedSubtotal >= 5000 ? 0 : 250;
  const total = discountedSubtotal + deliveryFee;

  document.getElementById("cartSubtotalText").innerText = formatPKR(subtotal);

  const discountRow = document.getElementById("discountRow");
  if (discountRow) {
    if (discount > 0) {
      discountRow.style.display = "flex";
      document.getElementById("cartDiscountText").innerText = `- ${formatPKR(discount)}`;
    } else {
      discountRow.style.display = "none";
    }
  }

  document.getElementById("cartDeliveryText").innerText = deliveryFee === 0 ? "FREE" : formatPKR(deliveryFee);
  document.getElementById("cartTotalText").innerText = formatPKR(total);

  // Free delivery threshold progress
  if (thresholdBar) {
    const pct = Math.min(100, (discountedSubtotal / 5000) * 100);
    thresholdBar.innerHTML = `
      <div style="font-weight:700; color: ${discountedSubtotal >= 5000 ? 'var(--color-success)' : 'var(--color-navy-dark)'};">
        ${discountedSubtotal >= 5000 ? '🎉 You unlocked FREE Express Delivery Across Pakistan!' : `Add ${formatPKR(5000 - discountedSubtotal)} more to unlock FREE Delivery.`}
      </div>
      <div class="threshold-progress"><div class="threshold-fill" style="width: ${pct}%;"></div></div>
    `;
  }
}

function updateCartItemQty(index, delta) {
  const cart = getCart();
  if (!cart[index]) return;

  cart[index].quantity += delta;
  if (cart[index].quantity <= 0) {
    cart.splice(index, 1);
  }
  saveCart(cart);
}

function removeCartItem(index) {
  const cart = getCart();
  cart.splice(index, 1);
  saveCart(cart);
}

function applyPromo() {
  const code = (document.getElementById("promoInput")?.value || "").trim().toUpperCase();
  const feedback = document.getElementById("promoMsg");
  if (!feedback) return;

  if (code === "VIP10") {
    discountRate = 0.10;
    appliedPromoCode = code;
    feedback.innerText = "✓ VIP10 applied: 10% discount!";
    feedback.style.color = "var(--color-success)";
  } else if (code === "MSF15") {
    discountRate = 0.15;
    appliedPromoCode = code;
    feedback.innerText = "✓ MSF15 applied: 15% discount!";
    feedback.style.color = "var(--color-success)";
  } else {
    discountRate = 0.0;
    appliedPromoCode = "";
    feedback.innerText = "Invalid coupon code. Try VIP10";
    feedback.style.color = "var(--color-error)";
  }
  renderCartDrawer();
}

// Checkout Flow
function openCheckoutModal() {
  const cart = getCart();
  if (cart.length === 0) return;

  toggleCart(false);

  const summaryBox = document.getElementById("checkoutSummaryBox");
  const subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  const discount = subtotal * discountRate;
  const discountedSubtotal = subtotal - discount;
  const deliveryFee = discountedSubtotal >= 5000 ? 0 : 250;
  const total = discountedSubtotal + deliveryFee;

  if (summaryBox) {
    summaryBox.innerHTML = `
      <strong>Order Summary (${cart.reduce((s, i) => s + i.quantity, 0)} items):</strong>
      <div style="margin-top: 6px;">${cart.map(i => `${i.name} (${i.selectedOption}) x${i.quantity}`).join("<br>")}</div>
      <div style="margin-top: 8px; border-top: 1px dashed var(--color-cream-border); padding-top: 6px;">
        <div>Subtotal: ${formatPKR(subtotal)}</div>
        ${discount > 0 ? `<div style="color:var(--color-success);">VIP Discount: - ${formatPKR(discount)}</div>` : ''}
        <div>Delivery (COD): ${deliveryFee === 0 ? 'FREE' : formatPKR(deliveryFee)}</div>
        <div style="font-weight:800; font-size:14px; margin-top:4px; color:var(--color-navy-primary);">
          Total Payable at Doorstep: ${formatPKR(total)}
        </div>
      </div>
    `;
  }

  document.getElementById("checkoutModal")?.classList.add("open");
}

function closeCheckoutModal() {
  document.getElementById("checkoutModal")?.classList.remove("open");
}

function handlePlaceOrder(e) {
  e.preventDefault();
  const cart = getCart();
  const errBox = document.getElementById("checkoutErrorMsg");
  if (errBox) errBox.style.display = "none";

  if (cart.length === 0) {
    if (errBox) {
      errBox.innerText = "Your shopping bag is empty.";
      errBox.style.display = "block";
    }
    return;
  }

  // Stock check
  const products = getProducts();
  for (const item of cart) {
    const p = products.find(prod => prod.id === item.productId);
    if (!p) {
      errBox.innerText = `Product "${item.name}" is no longer available.`;
      errBox.style.display = "block";
      return;
    }
    if (p.stock < item.quantity) {
      errBox.innerText = `Insufficient stock for "${item.name}". Only ${p.stock} remaining.`;
      errBox.style.display = "block";
      return;
    }
  }

  // Decrement Stock
  for (const item of cart) {
    const p = products.find(prod => prod.id === item.productId);
    if (p) p.stock = Math.max(0, p.stock - item.quantity);
  }
  saveProducts(products);

  // Calculate totals
  const subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  const discount = subtotal * discountRate;
  const discountedSubtotal = subtotal - discount;
  const deliveryFee = discountedSubtotal >= 5000 ? 0 : 250;
  const total = discountedSubtotal + deliveryFee;

  const orderId = "MSF-" + Math.floor(10000 + Math.random() * 90000);
  const order = {
    orderId: orderId,
    customerName: document.getElementById("custName").value.trim(),
    customerPhone: document.getElementById("custPhone").value.trim(),
    customerEmail: document.getElementById("custEmail").value.trim(),
    deliveryAddress: document.getElementById("custAddress").value.trim(),
    city: document.getElementById("custCity").value,
    orderNotes: document.getElementById("custNotes").value.trim(),
    paymentMethod: "Cash on Delivery",
    totalAmount: total,
    status: "Pending",
    itemsSummary: cart.map(i => `${i.name} (${i.selectedOption}) x${i.quantity}`).join(", "),
    date: new Date().toLocaleDateString()
  };

  const orders = getOrders();
  orders.unshift(order);
  saveOrders(orders);

  // Clear cart
  saveCart([]);
  discountRate = 0.0;
  appliedPromoCode = "";

  closeCheckoutModal();

  // Show Success Modal
  document.getElementById("confirmedOrderId").innerText = orderId;
  document.getElementById("orderSuccessModal")?.classList.add("open");
}

function closeOrderSuccessModal() {
  document.getElementById("orderSuccessModal")?.classList.remove("open");
  navigateTo("home");
}

function trackConfirmedOrder() {
  const orderId = document.getElementById("confirmedOrderId")?.innerText;
  closeOrderSuccessModal();
  navigateTo("tracking");
  document.getElementById("trackingQueryInput").value = orderId;
  handleTrackSearch();
}

// Order Tracking Search
function handleTrackSearch() {
  const query = (document.getElementById("trackingQueryInput")?.value || "").trim().toLowerCase();
  const container = document.getElementById("trackingResultContainer");
  if (!container) return;

  if (!query) {
    container.innerHTML = `<p style="text-align:center; color:var(--color-text-secondary);">Please enter your Order ID or Phone number.</p>`;
    return;
  }

  const orders = getOrders().filter(o =>
    o.orderId.toLowerCase().includes(query) ||
    o.customerPhone.toLowerCase().includes(query) ||
    (o.customerEmail && o.customerEmail.toLowerCase().includes(query))
  );

  if (orders.length === 0) {
    container.innerHTML = `
      <div class="tracking-card text-center">
        <h4>No Matching Orders Found</h4>
        <p style="color:var(--color-text-secondary); margin-top:6px;">Please verify your Order ID (e.g. <code>MSF-12345</code>) or phone number.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = orders.map(o => createTrackingCardHtml(o)).join("");
}

function createTrackingCardHtml(order) {
  const steps = ["Pending", "Confirmed", "Shipped", "Delivered"];
  const stepIdx = {
    pending: 0,
    confirmed: 1,
    shipped: 2,
    delivered: 3,
    cancelled: -1
  }[order.status.toLowerCase()] || 0;

  return `
    <div class="tracking-card">
      <div class="flex-between">
        <div>
          <h3 style="font-size:18px;">${order.orderId}</h3>
          <span style="font-size:11px; color:var(--color-text-muted);">Placed on ${order.date}</span>
        </div>
        <span class="stock-pill stock-vip" style="position:static; font-size:11px;">${order.status.toUpperCase()}</span>
      </div>

      <div class="tracking-timeline">
        ${steps.map((st, i) => `
          <div class="timeline-step">
            <div class="step-bubble ${i <= stepIdx ? 'completed' : ''}">${i <= stepIdx ? '✓' : i + 1}</div>
            <span class="step-name">${st}</span>
          </div>
        `).join("")}
      </div>

      <div style="border-top: 1px solid var(--color-cream-border); padding-top: 12px; font-size: 13px;">
        <p><strong>Items:</strong> ${order.itemsSummary}</p>
        <p><strong>Recipient:</strong> ${order.customerName} (${order.customerPhone})</p>
        <p><strong>Address:</strong> ${order.deliveryAddress}, ${order.city}</p>
        <p><strong>Payment:</strong> ${order.paymentMethod}</p>
        <p style="font-weight:700; margin-top:6px; font-size:14px; color:var(--color-navy-primary);">
          COD Amount Due: ${formatPKR(order.totalAmount)}
        </p>
      </div>
    </div>
  `;
}

// Contact Form
function handleContactSubmit(e) {
  e.preventDefault();
  document.getElementById("contactSuccess").style.display = "block";
  e.target.reset();
}

function handleNewsletter(e) {
  e.preventDefault();
  document.getElementById("newsletterSuccess").style.display = "block";
  document.getElementById("newsletterInput").value = "";
}

// Admin Portal
function renderAdminPortal() {
  const container = document.getElementById("adminContainer");
  if (!container) return;

  if (!isAdminAuthenticated) {
    container.innerHTML = `
      <div class="admin-login-card">
        <h2 style="color:var(--color-gold-light); font-size:22px; margin-bottom:6px;">Staff Authorization</h2>
        <p style="color:rgba(255,255,255,0.7); font-size:12px; margin-bottom:20px;">Enter master PIN to manage M Shakeel live operations (Default: <code>1234</code>)</p>
        <input type="password" id="adminPinInput" placeholder="Enter PIN (1234)" style="width:100%; padding:10px; border-radius:6px; border:1px solid var(--color-gold-primary); text-align:center; font-size:16px; margin-bottom:14px;">
        <button class="btn btn-primary btn-block" onclick="checkAdminPin()">Unlock Dashboard</button>
        <div id="adminPinError" style="color:var(--color-error); font-size:12px; margin-top:10px; display:none;">Invalid PIN. Use 1234</div>
      </div>
    `;
    return;
  }

  const orders = getOrders();
  const products = getProducts();
  const totalRevenue = orders.filter(o => o.status.toLowerCase() !== "cancelled").reduce((s, o) => s + o.totalAmount, 0);

  container.innerHTML = `
    <div class="flex-between" style="margin-bottom:20px;">
      <div>
        <h2 style="font-size:20px;">Operations & Catalog Management</h2>
        <span style="font-size:12px; color:var(--color-text-secondary);">Logged in as Store Administrator</span>
      </div>
      <button class="btn btn-outline" style="border-color:var(--color-navy-primary); color:var(--color-navy-primary);" onclick="logoutAdmin()">Lock Portal</button>
    </div>

    <!-- Analytics Cards -->
    <div style="display:grid; grid-template-columns:repeat(3, 1fr); gap:16px; margin-bottom:24px;">
      <div style="background:var(--color-navy-dark); color:#FFF; padding:18px; border-radius:8px;">
        <span style="font-size:11px; color:var(--color-gold-light); font-weight:700;">TOTAL REVENUE</span>
        <h3 style="color:var(--color-gold-accent); font-size:22px;">${formatPKR(totalRevenue)}</h3>
      </div>
      <div style="background:var(--color-cream-surface); border:1px solid var(--color-cream-border); padding:18px; border-radius:8px;">
        <span style="font-size:11px; color:var(--color-text-secondary); font-weight:700;">TOTAL ORDERS</span>
        <h3 style="font-size:22px; color:var(--color-navy-dark);">${orders.length}</h3>
      </div>
      <div style="background:var(--color-cream-surface); border:1px solid var(--color-cream-border); padding:18px; border-radius:8px;">
        <span style="font-size:11px; color:var(--color-text-secondary); font-weight:700;">ACTIVE CATALOG</span>
        <h3 style="font-size:22px; color:var(--color-navy-dark);">${products.length} Items</h3>
      </div>
    </div>

    <!-- Admin Tabs -->
    <div class="admin-tabs">
      <button class="admin-tab-btn active" onclick="switchAdminTab('orders', this)">Customer Orders (${orders.length})</button>
      <button class="admin-tab-btn" onclick="switchAdminTab('products', this)">Catalog & Inventory</button>
    </div>

    <div id="adminTabOrders">
      <table class="admin-table">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>City</th>
            <th>Items</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          ${orders.map((o, idx) => `
            <tr>
              <td><strong>${o.orderId}</strong><br><small>${o.date}</small></td>
              <td>${o.customerName}<br><small>${o.customerPhone}</small></td>
              <td>${o.city}</td>
              <td><small>${o.itemsSummary}</small></td>
              <td><strong>${formatPKR(o.totalAmount)}</strong></td>
              <td><span class="stock-pill stock-vip" style="position:static;">${o.status}</span></td>
              <td>
                <select onchange="updateOrderStatus('${o.orderId}', this.value)" style="padding:4px 8px; font-size:11px; border-radius:4px;">
                  <option value="Pending" ${o.status === 'Pending' ? 'selected' : ''}>Pending</option>
                  <option value="Confirmed" ${o.status === 'Confirmed' ? 'selected' : ''}>Confirmed</option>
                  <option value="Shipped" ${o.status === 'Shipped' ? 'selected' : ''}>Shipped</option>
                  <option value="Delivered" ${o.status === 'Delivered' ? 'selected' : ''}>Delivered</option>
                  <option value="Cancelled" ${o.status === 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                </select>
              </td>
            </tr>
          `).join("")}
        </tbody>
      </table>
    </div>

    <div id="adminTabProducts" style="display:none;">
      <div class="flex-between" style="margin-bottom:12px;">
        <h3>Catalog Products</h3>
        <button class="btn btn-primary btn-sm" onclick="promptAddProduct()">+ Add New Fabric</button>
      </div>
      <table class="admin-table">
        <thead>
          <tr>
            <th>Product</th>
            <th>Category</th>
            <th>Fabric Type</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          ${products.map(p => `
            <tr>
              <td><strong>${p.name}</strong></td>
              <td>${p.category}</td>
              <td>${p.fabricType}</td>
              <td>${formatPKR(p.price)}</td>
              <td>
                <input type="number" value="${p.stock}" style="width:60px; padding:3px;" onchange="updateProductStock(${p.id}, this.value)">
              </td>
              <td>
                <button style="color:var(--color-error); background:none; border:none; cursor:pointer;" onclick="deleteCatalogProduct(${p.id})">Delete</button>
              </td>
            </tr>
          `).join("")}
        </tbody>
      </table>
    </div>
  `;
}

function checkAdminPin() {
  const pin = document.getElementById("adminPinInput")?.value;
  if (pin === "1234" || pin === "MSF2026") {
    isAdminAuthenticated = true;
    renderAdminPortal();
  } else {
    document.getElementById("adminPinError").style.display = "block";
  }
}

function logoutAdmin() {
  isAdminAuthenticated = false;
  renderAdminPortal();
}

function switchAdminTab(tabName, btn) {
  document.querySelectorAll(".admin-tab-btn").forEach(b => b.classList.remove("active"));
  btn.classList.add("active");

  document.getElementById("adminTabOrders").style.display = tabName === "orders" ? "block" : "none";
  document.getElementById("adminTabProducts").style.display = tabName === "products" ? "block" : "none";
}

function updateOrderStatus(orderId, newStatus) {
  const orders = getOrders();
  const o = orders.find(ord => ord.orderId === orderId);
  if (o) {
    o.status = newStatus;
    saveOrders(orders);
    alert(`Order ${orderId} updated to ${newStatus}`);
    renderAdminPortal();
  }
}

function updateProductStock(productId, newStock) {
  const products = getProducts();
  const p = products.find(prod => prod.id === productId);
  if (p) {
    p.stock = Math.max(0, parseInt(newStock) || 0);
    saveProducts(products);
  }
}

function deleteCatalogProduct(productId) {
  if (!confirm("Are you sure you want to remove this product from the catalog?")) return;
  const products = getProducts().filter(p => p.id !== productId);
  saveProducts(products);
  renderAdminPortal();
}

function promptAddProduct() {
  const name = prompt("Enter Fabric Name (e.g. Royal Karandi Charcoal Edition):");
  if (!name) return;
  const category = prompt("Enter Category (Men's Wear, Women's Wear, Unstitched Fabrics, New Arrivals):", "Men's Wear");
  const price = parseFloat(prompt("Enter Price in Rs:", "6500")) || 6500;
  const fabricType = prompt("Enter Fabric Type (e.g. 100% Raw Karandi):", "Pure Cotton");
  const stock = parseInt(prompt("Initial Stock Units:", "15")) || 15;

  const products = getProducts();
  products.unshift({
    id: Date.now(),
    name: name,
    category: category,
    price: price,
    origPrice: price * 1.15,
    image: "https://images.unsplash.com/photo-1594938298603-c8148c4dae35?w=800",
    fabricType: fabricType,
    description: "Handcrafted luxury eastern fabric by M Shakeel Fabrics.",
    options: ["4.5 Meters (Full Suit)", "7 Meters (Double Suit)"],
    stock: stock,
    featured: true,
    rating: 5.0,
    reviews: 1
  });
  saveProducts(products);
  renderAdminPortal();
}

// Initial Boot
document.addEventListener("DOMContentLoaded", () => {
  updateCartBadge();
  renderFeaturedProducts();
});
