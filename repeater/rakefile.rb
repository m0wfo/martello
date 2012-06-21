desc "Compile"
task :build do
  puts 'Compiling...'
  system("javac ./src/*.java -d .")
  puts 'Packaging...'
  system("jar cmf Manifest.txt repeater.jar martello/*")
  puts 'Done'
end

desc "Remove build products"
task :clean do
  system("rm -rf martello repeater.jar")
end
