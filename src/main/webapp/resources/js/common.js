function toggleSubmenu(clickedItem) {
  const allItems = document.querySelectorAll('.sidebar-item');
  const allSubmenus = document.querySelectorAll('.sidebar-submenu');

  allItems.forEach(item => {
    if (item !== clickedItem) {
      item.classList.remove('active');
    }
  });

  allSubmenus.forEach(sub => {
    if (sub.previousElementSibling !== clickedItem) {
      sub.classList.remove('open');
    }
  });

  clickedItem.classList.toggle('active');
  const next = clickedItem.nextElementSibling;
  if (next && next.classList.contains('sidebar-submenu')) {
    next.classList.toggle('open');
  }
}

