from setuptools import setup

setup(
    name='earthquakes',
    version='0.2',
    description='Simple interface to the USGS site to get earthquake data',
    author='acbart, jason-riddle, ofsaleem',
    author_email='acbart@vt.edu',
    url='http://mickey.cs.vt.edu/realtimeweb',
    packages=['earthquakes'],
      long_description="""\
      Simple interface to the USGS site to get earthquake data
      """,
      classifiers=[
          "License :: OSI Approved :: GNU General Public License (GPL)",
          "Programming Language :: Python",
          "Development Status :: 4 - Beta",
          "Intended Audience :: Teachers",
          "Topic :: Education",
      ],
      keywords='earthquake usgs realtimeweb',
      license='GPL',
      install_requires=[
        'setuptools',
      ],
      )