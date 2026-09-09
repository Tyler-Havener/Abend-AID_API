   
   <script type="text/javascript">
        function abendAPI(selectedValue) {
            var element = document.getElementById('reportNum')
            (selectedValue === 'report') ? element.style.display = 'block' : element.style.display = 'none'
        }

        document.addEventListener("DOMContentLoaded", function() {
            var dropdown = document.getElementById('apiDropdown')
            if (dropdown) {
                abendAPI(dropdown.value)
            }
        })
    </script>
