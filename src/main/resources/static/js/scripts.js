// Reactive behaviors: hero slideshow with random images, search+filter, mobile nav, theme toggle
document.addEventListener('DOMContentLoaded', () => {
  // Footer year
  const yearEl = document.getElementById('year');
  if (yearEl) yearEl.textContent = new Date().getFullYear();

  // MOBILE NAV
  const navToggle = document.getElementById('nav-toggle');
  const mainMenu = document.getElementById('main-menu');
  if (navToggle && mainMenu) {
    navToggle.addEventListener('click', () => {
      const showing = mainMenu.style.display === 'flex';
      mainMenu.style.display = showing ? 'none' : 'flex';
    });
    // hide on narrow screens
    if (window.matchMedia('(max-width:880px)').matches) mainMenu.style.display = 'none';
  }

  // THEME TOGGLE
  const themeBtn = document.getElementById('theme-toggle');
  if (themeBtn) {
    themeBtn.addEventListener('click', () => {
      const alt = document.body.classList.toggle('alt-theme');
      themeBtn.setAttribute('aria-pressed', String(alt));
      themeBtn.textContent = alt ? 'Red/White' : 'Theme';
    });
  }

  // RANDOM HERO IMAGES (tries local assets/images/randomX.jpg, fallback to Unsplash)
  const heroDecor = document.getElementById('hero-decor');
  const localImages = [
    'assets/images/random1.jpg',
    'assets/images/random2.jpg',
    'assets/images/random3.jpg',
    'assets/images/random4.jpg',
    'assets/images/random5.jpg'
  ];

  // Unsplash fallback images (safe to use)
  const fallback = [
    'https://images.unsplash.com/photo-1503676260728-1c00da094a0b?q=80&w=1920&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1523050854058-8df90110c9f1?q=80&w=1920&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1531219432768-2a0b6d37f0a8?q=80&w=1920&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1508873535684-277a3cbccf9e?q=80&w=1920&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1551836022-d5d88e9218df?q=80&w=1920&auto=format&fit=crop'
  ];

  // Helper: test if local image exists by trying to load it
  async function imageExists(url) {
    return new Promise(resolve => {
      const img = new Image();
      img.onload = () => resolve(true);
      img.onerror = () => resolve(false);
      img.src = url;
    });
  }

  // Build the actual slideshow list using available images
  (async function prepareHeroImages() {
    const available = [];
    for (let i = 0; i < localImages.length; i++) {
      // check locals; if not found, skip (we'll use fallback)
      // NOTE: on file:// some browsers may block this check; it's okay, fallback covers it
      // we still push local URL optimistically
      const ok = await imageExists(localImages[i]);
      if (ok) available.push(localImages[i]);
    }
    // if none local, use fallback
    const sources = available.length ? available : fallback.slice();

    // shuffle for randomness
    for (let i = sources.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [sources[i], sources[j]] = [sources[j], sources[i]];
    }

    // set first background immediately
    let index = 0;
    function setHeroBackground(idx) {
      if (!heroDecor) return;
      const url = sources[idx % sources.length];
      heroDecor.style.backgroundImage = `url("${url}")`;
    }
    setHeroBackground(index);

    // rotate every 5 seconds
    setInterval(() => {
      index = (index + 1) % sources.length;
      setHeroBackground(index);
    }, 5000);
  })();

  // SEARCH + FILTER for course cards
  const searchInput = document.getElementById('search-input');
  const filterSelect = document.getElementById('filter');
  const courseGrid = document.getElementById('course-grid');

  function applyFilters() {
    const q = (searchInput && searchInput.value || '').trim().toLowerCase();
    const type = filterSelect ? filterSelect.value : 'all';
    const cards = courseGrid ? Array.from(courseGrid.querySelectorAll('.course-card')) : [];
    cards.forEach(card => {
      const title = (card.textContent || '').trim().toLowerCase();
      const cardType = (card.getAttribute('data-type') || 'all').toLowerCase();
      const matchQ = q === '' || title.includes(q);
      const matchType = type === 'all' || cardType === type;
      card.style.display = (matchQ && matchType) ? '' : 'none';
    });
  }

  if (searchInput) searchInput.addEventListener('input', applyFilters);
  if (filterSelect) filterSelect.addEventListener('change', applyFilters);

  // Smooth scrolling for internal anchors
  document.querySelectorAll('a[href^="#"]').forEach(a => {
    a.addEventListener('click', (e) => {
      const href = a.getAttribute('href');
      if (href.length > 1) {
        e.preventDefault();
        const target = document.querySelector(href);
        if (target) target.scrollIntoView({behavior: 'smooth', block: 'start'});
      }
    });
  });
});
