# M SHAKEEL FABRICS - Luxury E-Commerce Website & Mobile Application

A complete, responsive clothing and fabric e-commerce platform for **M SHAKEEL FABRICS**, Pakistan's premier eastern textile house.

This project delivers **both**:
1. **Full Responsive Website** (`/web/` directory with `index.html`, `styles.css`, and `app.js`) ready to run in any browser, deploy on Vercel/Netlify, or host anywhere.
2. **Native Android Application** (Kotlin + Jetpack Compose + Room SQLite) running in the AI Studio Streaming Android Emulator with an embedded **Web Store Mode** toggle.

---

## Brand Aesthetic & Identity

- **Brand Name**: M SHAKEEL FABRICS (Est. 1928)
- **Tagline**: *“Wear Your Style. Feel the Difference.”*
- **Color Palette**:
  - **Warm Cream**: `#FBF8F2`, `#FFFDF9`, `#F3EDE2`
  - **Deep Navy**: `#0A1420`, `#0D1B2A`, `#1B263B`
  - **Muted Gold**: `#C5A059`, `#D4AF37`, `#F3E7C4`

---

## 1. Web Application (`/web/`)

The web app is a standalone, responsive, single-page e-commerce application located in `/web/`:
- `web/index.html`: Responsive HTML5 semantic layout.
- `web/styles.css`: Luxury aesthetic with desktop, tablet, and mobile views.
- `web/app.js`: Reactive shopping cart, catalog filtering, stock management, Cash on Delivery checkout, order tracking, and PIN-protected admin portal.

### How to Run the Website Locally
You can open `/web/index.html` directly in any web browser, or serve it using Python or Node:
```bash
# Using Python
cd web && python3 -m http.server 8080

# Using Node.js
npx serve web
```
Then visit `http://localhost:8080` in your browser.

---

## 2. Android App (Streaming Emulator)

In Google AI Studio, this workspace was provisioned as an Android environment. The app includes:
- **Native Storefront**: Home, Catalog with search & filter chips, Product Detail with fabric meterage options (4.5m, 7m, Stitched sizes), Persistent Cart with coupon discounts (`VIP10` and `MSF15`), Cash on Delivery (COD) checkout, and live order tracking.
- **Web Store Mode**: Tap the **Globe icon (🌐)** in the top header to toggle the full responsive website view inside the emulator!
- **Admin Dashboard**: Tap the **Lock icon (🔒)** in the top header and enter PIN **`1234`** to manage customer orders, update delivery statuses, adjust stock, or add products.

---

## Order Process & Features

1. **Browse & Search**: Customers browse through Men's Wear, Women's Wear, Unstitched Fabrics, and New Arrivals with real-time search.
2. **Select Fabric Options**: Choose between 4.5m suit yardage, 7m double suit, or standard stitched sizes.
3. **Shopping Bag**: Change quantities, remove items, apply coupon `VIP10` for 10% off, and track the free delivery threshold (Rs. 5,000).
4. **Checkout**: Enter recipient name, mobile number, address, and city. Cash on Delivery is enabled by default.
5. **Stock Validation**: Inventory is automatically checked and reduced in the database.
6. **Order Confirmation & Tracking**: Receive unique Order ID (`#MSF-XXXXX`) and trace status from *Pending* ➔ *Confirmed* ➔ *Shipped* ➔ *Delivered*.
7. **Admin Area**: Access via PIN `1234` to update statuses, add/edit/delete products, and review store revenue.
